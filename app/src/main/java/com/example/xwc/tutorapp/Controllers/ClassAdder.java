package com.example.xwc.tutorapp.Controllers;

/**
 * Created by jameszhang on 17/10/2017.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.content.Intent;
import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;
import android.view.*;
import android.content.ContentValues;

import java.util.ArrayList;


public class ClassAdder extends AppCompatActivity {
    private Button add_class_button;
    private EditText txtCourse;
    private EditText txtStartTime;
    private EditText txtEndTime;
    private EditText txtDay;
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
        txtDay = (EditText) findViewById(R.id.txtDay);
        txtLocation = (EditText) findViewById(R.id.txtLocation);

        Button add_class_button = (Button) findViewById(R.id.btnAddNewClass);
        Button btnDelete = (Button) findViewById(R.id.btnDeleteClass);


        // Set events for UI elements
        add_class_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.CLASSES_CLASS_ID, txtCourse.getText().toString());
                insertValues.put(DBOpenHelper.CLASSES_DAY, txtDay.getText().toString());
                insertValues.put(DBOpenHelper.CLASSES_ENDTIME, txtEndTime.getText().toString());
                insertValues.put(DBOpenHelper.CLASSES_STARTTIME, txtStartTime.getText().toString());
                insertValues.put(DBOpenHelper.CLASSES_LOCATION, txtLocation.getText().toString());
                // TODO: Make name editable
                insertValues.put(DBOpenHelper.CLASSES_TUTOR, "Jacob");

                if (updating_class_id != null) {
                    getContentResolver().update(ClassProvider.CONTENT_URI,
                            insertValues, DBOpenHelper.CLASSES_CLASS_ID + " = ?", new String[] {updating_class_id});
                } else {
                    getContentResolver().insert(ClassProvider.CONTENT_URI,
                            insertValues);
                }

                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getContentResolver().delete(ClassProvider.CONTENT_URI,
                            DBOpenHelper.CLASSES_CLASS_ID + " = ?", new String[] {updating_class_id});
                finish();
            }
        });

        // Check if we are editing or making a new class
        Intent intent = getIntent();
        if (intent.hasExtra("CLASSID")) {
            updating_class_id = intent.getStringExtra("CLASSID");
            // Fill in data from DB
            txtCourse.setText(intent.getStringExtra("CLASSID"));
            txtDay.setText(intent.getStringExtra("DAY"));
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
}

