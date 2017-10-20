package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;
import com.example.xwc.tutorapp.R;


/**
 * Created by jameszhang on 20/10/2017.
 */

public class NameGameScore extends AppCompatActivity {
    private TextView lblScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namegame_score_activity);
        lblScore = (TextView) findViewById(R.id.lblNGActualScore);
        Intent i = getIntent();
        String scoreText = i.getIntExtra("SCORE", 0) + " out of " + i.getIntExtra("TOTAL", 0);
        lblScore.setText(scoreText);
    }
}
