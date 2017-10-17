package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.xwc.tutorapp.Model.Class;

import com.example.xwc.tutorapp.R;


public class ClassMenu extends AppCompatActivity implements View.OnClickListener {

    private TextView className;

    private Button viewRollHistory;

    private Button startTutorial;

    private Button editClass;

    private Class thisClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_menu);

        className = (TextView) findViewById(R.id.ClassName);
        viewRollHistory = (Button) findViewById(R.id.rollHistory);
        viewRollHistory.setOnClickListener(this);
        startTutorial = (Button) findViewById(R.id.newTut);
        editClass = (Button) findViewById(R.id.editClass);
        editClass.setOnClickListener(this);
        startTutorial.setOnClickListener(this);



        Intent intent = getIntent();
        if (intent.hasExtra("CLASSID")) {
            String thisClassName = intent.getStringExtra("CLASSID");
            String thisClassDay = intent.getStringExtra("CLASSID");
            String thisClassStart = intent.getStringExtra("STARTTIME");
            String thisClassEnd = intent.getStringExtra("ENDTIME");
            String thisClassLocation = intent.getStringExtra("LOCATION");

           thisClass = new Class(thisClassName,thisClassDay,thisClassStart,thisClassEnd,"",thisClassLocation,22,0.0);
            className.setText(thisClass.getClassId() + " " + thisClass.getDay() + " " + thisClass.getStartTime() +
                    "-" + thisClass.getEndTime() + " " + thisClass.getLocation());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.rollHistory:
                intent = new Intent(this, TutorialList.class);
                break;
            case R.id.newTut:
                intent = new Intent(this, TutorialStudentList.class);
                intent.putExtra("CLASSID", thisClass.getClassId());
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
        if (intent != null) {
            startActivity(intent);
        }
    }

}