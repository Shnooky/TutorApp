package com.example.xwc.tutorapp.Controllers;
import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;

import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

public class StudentProfile extends AppCompatActivity {
    private Spinner cboCurrentClass;
    private TextView lblGrade;
    private EditText txtZID;
    private EditText txtFirstName;
    private EditText txtSurname;
    private Spinner cboSkill;

    private Button btnUpdate;
    private Button btnDelete;

    private String updating_student = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        // Get references to UI elements
        cboCurrentClass = (Spinner) findViewById(R.id.cboStudentClass);
        lblGrade = (TextView) findViewById(R.id.lblStudentGrade);

        txtZID = (EditText) findViewById(R.id.txtStudentZID);
        txtFirstName = (EditText) findViewById(R.id.txtStudentFirstname);
        txtSurname = (EditText) findViewById(R.id.txtStudentSurname);
        cboSkill = (Spinner) findViewById(R.id.cboCodingSkill);

        btnUpdate = (Button) findViewById(R.id.btnUpdateStudent);
        btnDelete = (Button) findViewById(R.id.btnDeleteStudent);

        // Set up UI

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[] {
                "Low", "Moderate", "High", "Exceptional"
        });
        cboSkill.setAdapter(adapter);

        ArrayList<String> classes = new ArrayList<>();
        Cursor c = getContentResolver().query(ClassProvider.CONTENT_URI,
                DBOpenHelper.CLASSES_ALL_COLUMNS, null,null, null);
        while (c!= null && c.moveToNext()) {
            classes.add(c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)));
        }
        cboCurrentClass.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, classes.toArray(new String[classes.size()])));

        // Set up UI Events

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.STUDENTS_ZID, txtZID.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_CLASS, cboCurrentClass.getSelectedItem().toString());
                insertValues.put(DBOpenHelper.STUDENTS_FIRSTNAME, txtFirstName.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_SURNAME, txtSurname.getText().toString());
                insertValues.put(DBOpenHelper.STUDENTS_SKILL, cboSkill.getSelectedItem().toString());

                if (updating_student != null) {
                    // update existing student
                    getContentResolver().update(StudentProvider.CONTENT_URI,
                            insertValues, DBOpenHelper.STUDENTS_ZID + " = ?", new String[] {updating_student});
                } else {
                    // add new student
                    getContentResolver().insert(StudentProvider.CONTENT_URI,
                            insertValues);
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), StudentProfile.class);
                startActivity(i);

            }
        });


        // Check if we are adding or updating a student

        Intent i = getIntent();
        if (i.hasExtra("ZID")) {
            txtFirstName.setText(i.getStringExtra("FIRSTNAME").toString());
            txtSurname.setText(i.getStringExtra("SURNAME").toString());
            txtZID.setText(i.getStringExtra("ZID").toString());
//            lblGrade.setText(i.getStringExtra("GRADE").toString());
            selectSpinnerValue (cboCurrentClass, i.getStringExtra("CLASS").toString());
            txtFirstName.setText(i.getStringExtra("FIRSTNAME").toString());
            selectSpinnerValue(cboSkill, i.getStringExtra("SKILL").toString());
            updating_student = i.getStringExtra("ZID").toString();
            btnUpdate.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            updating_student = null;
            btnUpdate.setText("Add");
            btnDelete.setVisibility(View.INVISIBLE);
            lblGrade.setText("n/a");
        }
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
