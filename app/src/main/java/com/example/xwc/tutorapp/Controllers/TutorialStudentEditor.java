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
    private CheckBox chkPart;
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
        chkPart = (CheckBox) findViewById(R.id.chkPartSE);

        imgStudent = (ImageView) findViewById(R.id.imgStudentTutorial);

        lblStudentName = (TextView) findViewById(R.id.lblStudentTutorialName);
        lblStudentZID = (TextView) findViewById(R.id.lblStudentTutorialZID);


        // Set up UI Events

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_MARK, Double.parseDouble(txtGrade.getText().toString()));
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_LATE, chkLate.isChecked() ? 1 : 0);
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_ABSENT, chkAbsent.isChecked() ? 1 : 0);
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_PARTICIPATION, chkPart.isChecked() ? 1 : 0);
                getContentResolver().update(StudentTutorialProvider.CONTENT_URI,
                            insertValues, DBOpenHelper.STUDENTS_TUTORIALS_ZID + " = ? AND " +
                        DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID + " = ?", new String[] {currZID, currTutorialID});
                finish();
            }
        });

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
                    i.putExtra("PICTURE", c.getBlob(c.getColumnIndex(DBOpenHelper.STUDENTS_PICTURE)));
                    i.putExtra("SKILL", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SKILL)));
                    startActivityForResult(i, EDIT_STUDENT);
                }
            }
        });

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


        Intent i = getIntent();
        currTutorialID = i.getStringExtra("TUTORIALID");
        currZID = i.getStringExtra("ZID");
        lblStudentZID.setText(i.getStringExtra("ZID"));
        lblStudentName.setText(i.getStringExtra("NAME"));

        // Setup UI
        chkLate.setChecked(i.getIntExtra("LATE", 0) == 1);
        chkAbsent.setChecked(i.getIntExtra("ABSENT", 0) == 1);
        chkPresent.setChecked(!chkLate.isChecked() && !chkAbsent.isChecked()); // if none are checked, student must be present
        txtGrade.setText(i.getStringExtra("GRADE"));
        String participated = i.getStringExtra("PART");
        chkPart.setChecked(participated.equals("1"));
        imgStudent.setImageBitmap(StudentProfile.getImage(i.getByteArrayExtra("IMG")));
    }

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
