package com.example.xwc.tutorapp.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.R;
import android.content.Context;
import android.os.AsyncTask;
import android.app.ProgressDialog;
/*
Created by: Jacob and James on 15/10/2017
First activity - main menu.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context appContext;
    private Button mClassList;
    private Button mStudentProfile;
    private Button mNameGame;
    private Button btnDummyData;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();

        // Grab references to UI elements
        mClassList = (Button) findViewById(R.id.classes);
        mClassList.setOnClickListener(this);
        mStudentProfile = (Button) findViewById(R.id.student);
        mStudentProfile.setOnClickListener(this);
        mNameGame = (Button) findViewById(R.id.nameGame);
        mNameGame.setOnClickListener(this);
        btnDummyData = (Button) findViewById(R.id.btnDummyData);
        btnDummyData.setOnClickListener(this);

        //Check build versions for Camera Permissions.
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

    /*
    Set OnClickListener's for Classes, Student Manager and The Name Game.
     */
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

/*
Load dummy data upon button click
 */
    public void loadDummyData() {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Adding Sample Data...");
        pd.show();
        new DummyWorker().execute();
    }

    private class DummyWorker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DBOpenHelper helper = new DBOpenHelper(getBaseContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.createDummyData(database);
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            CommonMethods.showToast("Sample data loaded!");
            pd.cancel();
        }
    }
}
