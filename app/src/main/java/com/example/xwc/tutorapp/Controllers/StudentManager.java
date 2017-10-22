package com.example.xwc.tutorapp.Controllers;

/**
 * Created by Jacob and James on 17/10/2017.
 * Activity dedicated to Student Search functionalities.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.content.Intent;
import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Model.*;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;
import android.view.*;
import android.content.ContentValues;

import java.nio.DoubleBuffer;
import java.util.ArrayList;


public class StudentManager extends AppCompatActivity {
    private EditText txtSearch;
    private Button btnSearch;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_manager_activity);

        // Grab references to UI elements
        txtSearch = (EditText) findViewById(R.id.txtSearchStudent);
        btnSearch = (Button) findViewById(R.id.btnSearchStudents);
        btnAdd = (Button) findViewById(R.id.btnAddNewStudent);


        // Set events for UI elements
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String zid = txtSearch.getText().toString();
                String firstName = (txtSearch.getText().toString().contains(" ")) ? txtSearch.getText().toString().split(" ")[0] : "";
                String lastName = (txtSearch.getText().toString().contains(" ")) ? txtSearch.getText().toString().split(" ")[1] : "";

                //search for the student in the database.
                Cursor c = getContentResolver().query(StudentProvider.CONTENT_URI,
                        DBOpenHelper.STUDENTS_ALL_COLUMNS, DBOpenHelper.STUDENTS_ZID +
                                " = ? OR (" + DBOpenHelper.STUDENTS_FIRSTNAME
                        + "=? AND " + DBOpenHelper.STUDENTS_SURNAME + " = ?)",
                        new String[]{zid, firstName, lastName}, null);

                //if student found, create an intent with the data, to be sent to the StudentProfile activity.
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
                } else {
                    //If no student found, show a Toast message.
                    CommonMethods.showToast("No student found");
                }
            }
        });

        /*
        Creates an empty StudentProfile activity ready for data to be added.
         */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), StudentProfile.class);
                i.putExtra("HideGrades",true);
                startActivity(i);
            }
        });

    }
}

