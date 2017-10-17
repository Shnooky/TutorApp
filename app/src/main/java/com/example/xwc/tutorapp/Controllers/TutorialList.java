package com.example.xwc.tutorapp.Controllers;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        tutorialListView = (ListView) findViewById(R.id.tutoriallist);

        TutorialAdapter adapter = new TutorialAdapter(this, tuts);

        tutorialListView.setAdapter(adapter);


    }

    private void loadTutorials() {
        Cursor c = getContentResolver().query(TutorialProvider.CONTENT_URI,
                DBOpenHelper.TUTORIALS_ALL_COLUMNS, null,null, null);
        tuts.clear();
        while (c!= null && c.moveToNext()) {
            tuts.add(new Tutorial(c.getString(c.getColumnIndex(DBOpenHelper.TUTORIALS_ID)),
                    convertDate(c.getString(c.getColumnIndex(DBOpenHelper.TUTORIALS_DATE))),
                    0,
                    0
            ));
        }

        // Fill list
        TutorialAdapter adapter = new TutorialAdapter(this, tuts);
        tutorialListView.setAdapter(adapter);
    }

    private Date convertDate(String dateToConvert) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date returnDate = new Date();
        try {
            returnDate = df.parse(dateToConvert);
        } catch (ParseException e) {
           Log.e("DATEPARSE",e.toString());
        }
        return returnDate;
    }

}
