package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xwc.tutorapp.Adapters.TutorialAdapter;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.TutorialProvider;
import com.example.xwc.tutorapp.Model.*;
import com.example.xwc.tutorapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jacob on 15/10/2017.
 */

public class TutorialList extends AppCompatActivity {
    ArrayList<Tutorial> tuts = new ArrayList<>();
    ListView tutorialListView;
    SQLiteDatabase d;

    @Override
    public void onResume() {
        super.onResume();
        loadTutorials();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadTutorials();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        tutorialListView = (ListView) findViewById(R.id.tutoriallist);

        TutorialAdapter adapter = new TutorialAdapter(this, tuts);

        tutorialListView.setAdapter(adapter);

        Intent intent = getIntent();
       final String currentClass = intent.getStringExtra("CLASSID");

        tutorialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Tutorial t = tuts.get(position);
                Intent i = new Intent(getBaseContext(), TutorialStudentList.class);
                i.putExtra("TUTORIALID",t.getTutorialId());
                i.putExtra("TUTORIAL_CLASS",currentClass);
                startActivity(i);
            }
        });

        loadTutorials();

    }

    private void loadTutorials() {
        Intent intent = getIntent();
        String[] classID = {intent.getStringExtra("CLASSID")};
        if (intent.hasExtra("CLASSID")) {
            Cursor c =  DBOpenHelper.runSQL("select CLASSES.CLASS_ID, STUDENT_TUTORIALS.TUTORIAL_ID, " +
                    "(select TUTORIALS.DATE from TUTORIALS where TUTORIALS.TUTORIAL_ID = STUDENT_TUTORIALS.TUTORIAL_ID) AS 'TDATE', " +
                    "SUM(STUDENT_TUTORIALS.LATE) AS 'TOTALLATE', " +
                    "SUM(STUDENT_TUTORIALS.ABSENT) AS 'TOTALABSENT' FROM STUDENTS " +
                    "INNER JOIN CLASSES ON (STUDENTS.CLASS = CLASSES.CLASS_ID) " +
                    "INNER JOIN STUDENT_TUTORIALS ON (STUDENTS.ZID = STUDENT_TUTORIALS.ZID) " +
                    "WHERE STUDENTS.CLASS = ? GROUP BY CLASSES.CLASS_ID, STUDENT_TUTORIALS.TUTORIAL_ID, TDATE", classID) ;



            // getContentResolver().query(TutorialProvider.CONTENT_URI,
                    //DBOpenHelper.TUTORIALS_ALL_COLUMNS, DBOpenHelper.TUTORIALS_CLASS +"=?", classID, null);
            tuts.clear();
            while (c != null && c.moveToNext()) {
                tuts.add(new Tutorial(c.getString(c.getColumnIndex(DBOpenHelper.TUTORIALS_ID)),
                        c.getString(c.getColumnIndex("TDATE")),
                        c.getInt(c.getColumnIndex("TOTALABSENT")),
                        c.getInt(c.getColumnIndex("TOTALLATE"))
                ));
            }
        }

        // Fill list
        TutorialAdapter adapter = new TutorialAdapter(this, tuts);
        tutorialListView.setAdapter(adapter);
    }

}
