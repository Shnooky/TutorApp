package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.TutorialProvider;
import com.example.xwc.tutorapp.Model.Class;

import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Created by: Jacob and James on 15/10/2017
Upon choosing a class, the user arrives to the Class Menu, allowing them to view a history of tutorial rolls, start a new tutorial or edit a class's details.
 */
public class ClassMenu extends AppCompatActivity implements View.OnClickListener {
    public static final int EDIT_CLASS = 100;
    public static final int DELETE_CLASS = 101;
    public static final int UPDATE_CLASS = 102;
    private TextView className;
    private Button viewRollHistory;
    private Button startTutorial;
    private Button editClass;
    private Class thisClass;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_menu);

        // Grab references to UI elements
        className = (TextView) findViewById(R.id.ClassName);
        viewRollHistory = (Button) findViewById(R.id.rollHistory);
        viewRollHistory.setOnClickListener(this);
        startTutorial = (Button) findViewById(R.id.newTut);
        editClass = (Button) findViewById(R.id.editClass);
        editClass.setOnClickListener(this);
        startTutorial.setOnClickListener(this);


        /*
        Get information from the intent
         */
        Intent intent = getIntent();
        if (intent.hasExtra("CLASSID")) {
            setupClassUI(intent);
        }
    }

    /*
    Method dedicated to seting up the UI based on the Intent
     */
    public void setupClassUI(Intent intent) {
        String thisClassName = intent.getStringExtra("CLASSID");
        String thisClassDay = intent.getStringExtra("DAY");
        String thisClassStart = intent.getStringExtra("STARTTIME");
        String thisClassEnd = intent.getStringExtra("ENDTIME");
        String thisClassLocation = intent.getStringExtra("LOCATION");
        thisClass = new Class(thisClassName, thisClassDay, thisClassStart, thisClassEnd, "", thisClassLocation, 22, 0.0, 0);
        className.setText(thisClass.getClassId() + " " + thisClass.getDay() + " " + thisClass.getStartTime() +
                "-" + thisClass.getEndTime() + " " + thisClass.getLocation());
    }

    /*
    Set OnClickListener's for Roll History, New Tutorial or Edit Class.
     */
    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.rollHistory:
                if (CommonMethods.hasTutorials(thisClass.getClassId())) {
                    intent = new Intent(this, TutorialList.class);
                    intent.putExtra("CLASSID", thisClass.getClassId());
                    intent.putExtra("DAY", thisClass.getDay());
                    intent.putExtra("STARTTIME", thisClass.getStartTime());
                    intent.putExtra("ENDTIME", thisClass.getEndTime());
                    intent.putExtra("LOCATION", thisClass.getLocation());
                } else {
                    CommonMethods.showToast("There are no tutorials to display, please add a new tutorial");
                }
                break;
            case R.id.newTut:
                if (CommonMethods.hasStudents(thisClass.getClassId())) {
                    intent = new Intent(this, TutorialStudentList.class);
                    intent.putExtra("CLASSID", thisClass.getClassId());
                } else {
                    CommonMethods.showToast("There are no students in your class, please add some students first");
                }
                break;
            case R.id.editClass:
                intent = new Intent(getBaseContext(), ClassAdder.class);
                intent.putExtra("CLASSID", thisClass.getClassId());
                intent.putExtra("DAY", thisClass.getDay());
                intent.putExtra("STARTTIME", thisClass.getStartTime());
                intent.putExtra("ENDTIME", thisClass.getEndTime());
                intent.putExtra("LOCATION", thisClass.getLocation());
                break;
            default:
        }
        if (intent != null && v.getId() == R.id.editClass) {
            startActivityForResult(intent, EDIT_CLASS);
        } else if (intent != null) {
            startActivity(intent);
        }
    }

    /*
   If the class exists, reset the UI (this prevents the menu from being shown if a class has been deleted)
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_CLASS) {
            if (resultCode == DELETE_CLASS) {
                // Class deleted, so we must exit it's menu activity
                finish();
            } else if (resultCode == UPDATE_CLASS) {
                // refresh activity
                setupClassUI(data);
            }
        }
    }

}