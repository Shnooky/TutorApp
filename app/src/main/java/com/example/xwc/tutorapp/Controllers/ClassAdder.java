package com.example.xwc.tutorapp.Controllers;

/**
 * Created by jameszhang on 17/10/2017.
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
import com.example.xwc.tutorapp.Database.StudentTutorialProvider;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;
import android.view.*;
import android.content.ContentValues;
import android.util.Log;
import java.util.ArrayList;


public class ClassAdder extends AppCompatActivity {
    private Button add_class_button;
    private EditText txtCourse;
    private EditText txtStartTime;
    private EditText txtEndTime;
    private Spinner txtDay;
    private EditText txtLocation;
    private Button btnDelete;

    private String updating_class_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_edit_activity);

        // Grab references to UI elements
        txtCourse = (EditText) findViewById(R.id.txtCourse);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        txtDay = (Spinner) findViewById(R.id.txtDay);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[] {
                "Mon", "Tue", "Wed", "Thu", "Fri"
        });
        txtDay.setAdapter(adapter);
        txtLocation = (EditText) findViewById(R.id.txtLocation);

        Button add_class_button = (Button) findViewById(R.id.btnAddNewClass);
        Button btnDelete = (Button) findViewById(R.id.btnDeleteClass);


        // Set events for UI elements
        add_class_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String error = validateInput();
                if(error.isEmpty()) {
                    ContentValues insertValues = new ContentValues();
                    if(updating_class_id==null) {
                        insertValues.put(DBOpenHelper.CLASSES_CLASS_ID, generateID());
                    }
                    insertValues.put(DBOpenHelper.CLASSES_DAY, txtDay.getSelectedItem().toString());
                    insertValues.put(DBOpenHelper.CLASSES_ENDTIME, txtEndTime.getText().toString());
                    insertValues.put(DBOpenHelper.CLASSES_STARTTIME, txtStartTime.getText().toString());
                    insertValues.put(DBOpenHelper.CLASSES_LOCATION, txtLocation.getText().toString());
                    // TODO: Make name editable
                    insertValues.put(DBOpenHelper.CLASSES_TUTOR, "Jacob");
                    if (updating_class_id != null) {
                        getContentResolver().update(ClassProvider.CONTENT_URI,
                                insertValues, DBOpenHelper.CLASSES_CLASS_ID + " = ?", new String[]{updating_class_id});
                    } else {
                        getContentResolver().insert(ClassProvider.CONTENT_URI,
                                insertValues);
                    }

                    // Send updates back to class activity
                    Intent i = new Intent();
                    i.putExtra("CLASSID", updating_class_id);
                    i.putExtra("DAY", txtDay.getSelectedItem().toString());
                    i.putExtra("STARTTIME", txtStartTime.getText().toString());
                    i.putExtra("ENDTIME", txtEndTime.getText().toString());
                    i.putExtra("LOCATION", txtLocation.getText().toString());

                    setResult(ClassMenu.UPDATE_CLASS, i);
                    finish();
                } else {
                    CommonMethods.showToast(error);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = DBOpenHelper.runSQL("select * from STUDENTS where CLASS = ?", new String[]{updating_class_id});
                while (c != null && c.moveToNext()) {
                    Log.d("XWCABC", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ZID)));
                    // Delete every student_tutorial record associated with that class
                    getContentResolver().delete(StudentTutorialProvider.CONTENT_URI,
                            DBOpenHelper.STUDENTS_TUTORIALS_ZID + " = ?",
                            new String[] {c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ZID))});
                    // Delete that student
                    getContentResolver().delete(StudentProvider.CONTENT_URI,
                            DBOpenHelper.STUDENTS_ZID + " = ?",
                            new String[] {c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ZID))});
                }

                // Finally, delete the class
                getContentResolver().delete(ClassProvider.CONTENT_URI,
                        DBOpenHelper.CLASSES_CLASS_ID + " = ?", new String[] {updating_class_id});

                setResult(ClassMenu.DELETE_CLASS, null);
                finish();
            }
        });

        // Check if we are editing or making a new class
        Intent intent = getIntent();
        if (intent.hasExtra("CLASSID")) {
            updating_class_id = intent.getStringExtra("CLASSID");
            // Fill in data from DB
            txtCourse.setText(intent.getStringExtra("CLASSID"));
            int getIndex = determineDay(intent.getStringExtra("DAY"));
            txtDay.setSelection(getIndex);
            txtStartTime.setText(intent.getStringExtra("STARTTIME"));
            txtEndTime.setText(intent.getStringExtra("ENDTIME"));
            txtLocation.setText(intent.getStringExtra("LOCATION"));
            btnDelete.setVisibility(View.VISIBLE);
            add_class_button.setText("Update");
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
            updating_class_id = null;
        }
    }

    private String generateID() {
        String day = txtDay.getSelectedItem().toString();
        String dayTrunc = "";
        String startTime = txtStartTime.getText().toString();
        char newEndChar = 'A';
        switch(day) {
            case "Mon":
                dayTrunc = "M";
                break;
            case "Tue":
                dayTrunc = "T";
                break;
            case "Wed":
                dayTrunc = "W";
                break;
            case "Thu":
                dayTrunc = "H";
                break;
            case "Fri":
                dayTrunc = "F";
                break;
            default:
                dayTrunc = "?";
                break;
        }
        Cursor c = getContentResolver().query(ClassProvider.CONTENT_URI,DBOpenHelper.CLASSES_ALL_COLUMNS,DBOpenHelper.CLASSES_DAY+"=? AND "+
                DBOpenHelper.CLASSES_STARTTIME+"=?",new String[] {day,startTime},null);
        while (c != null && c.moveToNext()) {
            char endChar = c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)).charAt(3);
            int endCharAsNum = endChar+1;
            newEndChar = (char) endCharAsNum;
        }
        return dayTrunc+startTime.substring(0,2)+newEndChar;
    }

    private String validateInput() {
        String error="";
        String checkID = txtCourse.getText().toString();
        String checkStart = txtStartTime.getText().toString();
        String checkEnd = txtEndTime.getText().toString();
        String checkLocation = txtLocation.getText().toString();
        if(checkStart.length()!=4||checkEnd.length()!=4) {
            error = "Format should be 4 digit 24-hour time: i.e. 1200 for 12pm";
        } else if(checkLocation.isEmpty()) {
            error = "Please enter a location";
        }
        return error;
    }

    private int determineDay(String day) {
        int returnDay = 0;
        switch (day) {
            case "Mon":
                returnDay=0;
                break;
            case "Tue":
                returnDay=1;
                break;
            case "Wed":
                returnDay=2;
                break;
            case "Thu":
                returnDay=3;
                break;
            case "Fri":
                returnDay=4;
                break;
        }
        return returnDay;
    }
}

