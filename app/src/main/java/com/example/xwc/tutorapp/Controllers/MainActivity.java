package com.example.xwc.tutorapp.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.R;
import android.content.Context;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context appContext;
    private Button mClassList;
    private Button mStudentProfile;
    private Button mNameGame;
    private Button btnDummyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();
        //loadDummyData();
        mClassList = (Button) findViewById(R.id.classes);
        mClassList.setOnClickListener(this);
        mStudentProfile = (Button) findViewById(R.id.student);
        mStudentProfile.setOnClickListener(this);
        mNameGame = (Button) findViewById(R.id.nameGame);
        mNameGame.setOnClickListener(this);
        btnDummyData = (Button) findViewById(R.id.btnDummyData);
        btnDummyData.setOnClickListener(this);

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
                intent = new Intent(this, NameGameManager.class);
                break;
            case R.id.btnDummyData:
                loadDummyData();
                return;
        }
        if (intent !=null) {
            startActivity(intent);
        }

    }

    public void loadDummyData() {
        DBOpenHelper helper = new DBOpenHelper(getBaseContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.createDummyData(database);
    }
}
