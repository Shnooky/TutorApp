package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.xwc.tutorapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClassList;

    private Button mStudentProfile;

    private Button mNameGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClassList = (Button) findViewById(R.id.classes);
        mClassList.setOnClickListener(this);
        mStudentProfile = (Button) findViewById(R.id.student);
        mStudentProfile.setOnClickListener(this);
        mNameGame = (Button) findViewById(R.id.nameGame);
        mNameGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()) {
            case R.id.classes:
                intent = new Intent(this, ClassList.class);
                break;
            case R.id.student:
                intent = new Intent(this, StudentProfile.class);
                break;
            case R.id.nameGame:
                break;
            default:

        }
        if (intent !=null) {
            startActivity(intent);
        }

    }
}
