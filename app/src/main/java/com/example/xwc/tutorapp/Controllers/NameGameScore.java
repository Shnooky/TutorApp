package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import com.example.xwc.tutorapp.R;


/**
 * Created by Jacob and James on 20/10/2017.
 * Displays the user's score.
 */
public class NameGameScore extends AppCompatActivity {
    private TextView lblScore;
    private Button btnEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namegame_score_activity);
        lblScore = (TextView) findViewById(R.id.lblNGActualScore);
        Intent i = getIntent();
        String scoreText = i.getIntExtra("SCORE", 0) + " out of " + i.getIntExtra("TOTAL", 0);
        lblScore.setText(scoreText);
        //Buttons returns the user back to the home screen (MainActivity)
        btnEnd = (Button) findViewById(R.id.endGame);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
