package com.example.xwc.tutorapp.Controllers;
import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.Manifest;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Database.StudentTutorialProvider;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.Model.Student;
import com.example.xwc.tutorapp.R;
import android.content.pm.*;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.graphics.*;
import android.net.Uri;
import org.w3c.dom.Text;
import java.io.File;
import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap.CompressFormat;

/*
Created by: Jacob and James on 20/10/2017
Student manager on the tutorial level - allows user to edit participation and grade for the tutorial,
as well as attendance information (attended vs. absent vs. late), begin a consultation, or view their profile.
 */
public class TutorialStudentEditor extends AppCompatActivity {
    public static final int EDIT_STUDENT = 200;
    public static final int DELETE_STUDENT = 201;
    public static final int UPDATE_STUDENT = 202;
    private RadioButton chkAbsent;
    private RadioButton chkLate;
    private RadioButton chkPresent;

    private EditText txtGrade;
    private TextView lblStudentName;
    private TextView lblStudentZID;

    private Button btnUpdate;
    private Button btnViewProfile;
    private Button btnConsultation;
    private Button btnParticipation;
    private ImageView imgStudent;
    private String currZID, currTutorialID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_student_editor);

        // Get references to UI elements
        chkAbsent = (RadioButton) findViewById(R.id.chkAbsent);
        chkLate = (RadioButton) findViewById(R.id.chkLate);
        chkPresent = (RadioButton) findViewById(R.id.chkPresent);
        txtGrade = (EditText) findViewById(R.id.txtStudentGrade);
        btnUpdate = (Button) findViewById(R.id.btnUpdateStudentGrades);
        btnViewProfile = (Button) findViewById(R.id.btnViewStudentProfile);
        btnConsultation = (Button) findViewById(R.id.consultation);
        btnParticipation = (Button) findViewById(R.id.participation);
        imgStudent = (ImageView) findViewById(R.id.imgStudentTutorial);
        lblStudentName = (TextView) findViewById(R.id.lblStudentTutorialName);
        lblStudentZID = (TextView) findViewById(R.id.lblStudentTutorialZID);


        /*
       Set OnClickListener for Update Button
        */
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_MARK, Double.parseDouble(txtGrade.getText().toString()));
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_LATE, chkLate.isChecked() ? 1 : 0);
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_ABSENT, chkAbsent.isChecked() ? 1 : 0);

                getContentResolver().update(StudentTutorialProvider.CONTENT_URI,
                            insertValues, DBOpenHelper.STUDENTS_TUTORIALS_ZID + " = ? AND " +
                        DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID + " = ?", new String[] {currZID, currTutorialID});
                finish();
            }
        });

        /*
        Brings the user to the student's profile for subsequent updating
         */
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = getContentResolver().query(StudentProvider.CONTENT_URI,
                        DBOpenHelper.STUDENTS_ALL_COLUMNS, DBOpenHelper.STUDENTS_ZID +
                                " = ?",
                        new String[]{currZID}, null);

                if (c!= null && c.moveToNext()) {
                    Intent i = new Intent(getBaseContext(), StudentProfile.class);
                    i.putExtra("ZID", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_ZID)));
                    i.putExtra("CLASS", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_CLASS)));
                    i.putExtra("FIRSTNAME", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_FIRSTNAME)));
                    i.putExtra("SURNAME", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SURNAME)));
                    // TODO: CALCULATE GRADE FROM STUDENTS_TUTORIALS TABLE
                    //i.putExtra("GRADE", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_GRADE)));
                    i.putExtra("PICTURE", c.getBlob(c.getColumnIndex(DBOpenHelper.STUDENTS_PICTURE)));
                    i.putExtra("SKILL", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SKILL)));
                    startActivityForResult(i, EDIT_STUDENT);
                }
            }
        });

        /*
        Adds/removes a participation mark
         */
        btnParticipation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                if(btnParticipation.getText().equals("Add Participation Mark")) {
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_PARTICIPATION, 1);
                    getContentResolver().update(StudentTutorialProvider.CONTENT_URI, insertValues, DBOpenHelper.STUDENTS_TUTORIALS_ZID + " = ? AND " +
                            DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID + " = ?", new String[]{currZID, currTutorialID});
                    Log.d("d","participation mark added");
                    btnParticipation.setText("Remove Participation Mark");
                }
                else if(btnParticipation.getText().equals("Remove Participation Mark")) {
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_PARTICIPATION, 0);
                    getContentResolver().update(StudentTutorialProvider.CONTENT_URI, insertValues, DBOpenHelper.STUDENTS_TUTORIALS_ZID + " = ? AND " +
                            DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID + " = ?", new String[]{currZID, currTutorialID});
                    Log.d("d","participation mark removed");
                    btnParticipation.setText("Add Participation Mark");
                }
            }

        });

        /*
        Starts a consultation with the student.
         */
        btnConsultation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = getContentResolver().query(StudentProvider.CONTENT_URI,
                        DBOpenHelper.STUDENTS_ALL_COLUMNS, DBOpenHelper.STUDENTS_ZID +
                                " = ?",
                        new String[]{currZID}, null);
                if (c!= null && c.moveToNext()) {
                    Intent i = new Intent(getBaseContext(), StudentConsultation.class);
                    i.putExtra("SKILL",c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SKILL)));
                    i.putExtra("STUNAME",lblStudentName.getText());
                    startActivity(i);
                }
            }
        });

// Setup UI
        Intent i = getIntent();
        currTutorialID = i.getStringExtra("TUTORIALID");
        currZID = i.getStringExtra("ZID");
        lblStudentZID.setText(i.getStringExtra("ZID"));
        lblStudentName.setText(i.getStringExtra("NAME"));
        chkLate.setChecked(i.getIntExtra("LATE", 0) == 1);
        chkAbsent.setChecked(i.getIntExtra("ABSENT", 0) == 1);
        chkPresent.setChecked(!chkLate.isChecked() && !chkAbsent.isChecked()); // if none are checked, student must be present
        txtGrade.setText(i.getStringExtra("GRADE"));
        String participated = i.getStringExtra("PART");
        Log.d("participated? ",participated);
        if(participated.equals("1")) {
            btnParticipation.setText("Remove Participation Mark");
        } else {
            btnParticipation.setText("Add Participation Mark");
        }
        imgStudent.setImageBitmap(StudentProfile.getImage(i.getByteArrayExtra("IMG")));
    }

    /*
    If we are deleting a student, we don't want this screen to display itself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_STUDENT) {
            if (resultCode == DELETE_STUDENT) {
                // Student deleted, so we must exit the mark editor activity
                finish();
            } else if (resultCode == UPDATE_STUDENT) {
                imgStudent.setImageBitmap(StudentProfile.getImage(data.getByteArrayExtra("IMG")));
            }
        }
    }
}
