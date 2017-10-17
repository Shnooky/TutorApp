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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        tutorialListView = (ListView) findViewById(R.id.tutoriallist);

        TutorialAdapter adapter = new TutorialAdapter(this, tuts);

        tutorialListView.setAdapter(adapter);

        tutorialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Tutorial t = tuts.get(position);
                Intent i = new Intent(getBaseContext(), TutorialStudentList.class);
                i.putExtra("TUTORIALID",t.getTutorialId());
                startActivity(i);
            }
        });


    }

    private void loadTutorials() {
        Intent intent = getIntent();
        String[] classID = {intent.getStringExtra("CLASSID")};
        if (intent.hasExtra("CLASSID")) {
            Cursor c = getContentResolver().query(TutorialProvider.CONTENT_URI,
                    DBOpenHelper.TUTORIALS_ALL_COLUMNS, DBOpenHelper.TUTORIALS_CLASS +"=?", classID, null);
            tuts.clear();
            while (c != null && c.moveToNext()) {
                tuts.add(new Tutorial(c.getString(c.getColumnIndex(DBOpenHelper.TUTORIALS_ID)),
                        c.getString(c.getColumnIndex(DBOpenHelper.TUTORIALS_DATE)),
                        0,
                        0
                ));
            }
        }

        // Fill list
        TutorialAdapter adapter = new TutorialAdapter(this, tuts);
        tutorialListView.setAdapter(adapter);
    }

}
