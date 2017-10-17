package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.xwc.tutorapp.Adapters.TutorialAdapter;
import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import java.util.ArrayList;

/**
 * Created by Jacob on 15/10/2017.
 */

public class TutorialList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        ArrayList<Tutorial> tuts = new ArrayList<Tutorial>();

        ListView tutorialListView = (ListView) findViewById(R.id.tutoriallist);

        TutorialAdapter adapter = new TutorialAdapter(this, tuts);

        tutorialListView.setAdapter(adapter);


    }

}
