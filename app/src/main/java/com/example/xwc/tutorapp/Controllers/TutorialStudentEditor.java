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

public class TutorialStudentEditor extends AppCompatActivity {
    private CheckBox chkAbsent;
    private CheckBox chkLate;
    private EditText txtGrade;
    private TextView lblStudentName;
    private TextView lblStudentZID;

    private Button btnUpdate;
    private Button btnViewProfile;
    private ImageView imgStudent;
    private String currZID, currTutorialID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_student_editor);

        // Get references to UI elements
        chkAbsent = (CheckBox) findViewById(R.id.chkAbsent);
        chkLate = (CheckBox) findViewById(R.id.chkLate);

        txtGrade = (EditText) findViewById(R.id.txtStudentGrade);

        btnUpdate = (Button) findViewById(R.id.btnUpdateStudentGrades);
        btnViewProfile = (Button) findViewById(R.id.btnViewStudentProfile);

        imgStudent = (ImageView) findViewById(R.id.imgStudentTutorial);

        lblStudentName = (TextView) findViewById(R.id.lblStudentTutorialName);
        lblStudentZID = (TextView) findViewById(R.id.lblStudentTutorialZID);


        // Set up UI Events

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_MARK, Double.parseDouble(txtGrade.getText().toString()));
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_LATE, Boolean.toString(chkLate.isChecked()));
                insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_ABSENT, Boolean.toString(chkAbsent.isChecked()));

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
                    // TODO: CALCULATE GRADE FROM STUDENTS_TUTORIALS TABLE
                    //i.putExtra("GRADE", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_GRADE)));
                    i.putExtra("PICTURE", c.getBlob(c.getColumnIndex(DBOpenHelper.STUDENTS_PICTURE)));
                    i.putExtra("SKILL", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SKILL)));
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
        chkLate.setChecked(i.getBooleanExtra("LATE", false));
        chkAbsent.setChecked(i.getBooleanExtra("ABSENT", false));
        txtGrade.setText(i.getStringExtra("GRADE"));
    }


}
