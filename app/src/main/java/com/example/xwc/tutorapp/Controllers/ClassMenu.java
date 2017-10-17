package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xwc.tutorapp.R;


public class ClassMenu extends AppCompatActivity {

    private TextView className;

    private Button viewRollHistory;

    private Button startTutorial;

    private Button editClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_menu);

        className = (TextView) findViewById(R.id.ClassName);
        viewRollHistory = (Button) findViewById(R.id.rollHistory);
        startTutorial = (Button) findViewById(R.id.newTut);
        startTutorial = (Button) findViewById(R.id.editClass);

        Intent intent = getIntent();
        if (intent.hasExtra("CLASSID")) {
            className.setText(intent.getStringExtra("CLASSID")+ " " + intent.getStringExtra("DAY") + " " + intent.getStringExtra("STARTTIME") +
                    "-" + intent.getStringExtra("ENDTIME")+" "+intent.getStringExtra("LOCATION"));
        }

    }
}
