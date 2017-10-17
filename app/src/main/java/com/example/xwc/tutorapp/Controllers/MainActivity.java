package com.example.xwc.tutorapp.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.xwc.tutorapp.Database.DBOpenHelper;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        1002);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1002);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1002);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()) {
            case R.id.classes:
                intent = new Intent(this, ClassList.class);
                break;
            case R.id.student:
                intent = new Intent(this, StudentManager.class);
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
