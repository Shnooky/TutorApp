package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.R;

import org.w3c.dom.Text;
/*
Created by Jacob and James on 21/10/2017.
Presents a timer with varying time limits based on the student's coding skill.
 */
public class StudentConsultation extends AppCompatActivity {
    private TextView mStuName;
    private TextView mSkill;
    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;
    private TextView mCountdown;
    private Button mEndConsultation;

    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_consultation);

        mStuName = (TextView) findViewById(R.id.zid);
        mSkill = (TextView) findViewById(R.id.codingLevel);
        Intent intent = getIntent();
        if (intent.hasExtra("SKILL")) {
            mStuName.setText(intent.getStringExtra("STUNAME")+"'s coding skill is:");
            mSkill.setText(intent.getStringExtra("SKILL"));
        }
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setProgress(progress);
        mCountdown = (TextView) findViewById(R.id.countdown);
        final long startTime = calculate();
        //create a CountDownTimer object based on the calculated max milliseconds, count up by 1 second.
        mCountDownTimer = new CountDownTimer(startTime, 1000) {
            @Override
            //what happens after every second (1000 milliseconds)
            public void onTick(long l) {
                mCountdown.setText(Long.toString(l / 1000));
                progress++;
                //update progress-bar based on timer's completion
                mProgressBar.setProgress((int)(progress*100/(startTime/1000)));
            }

            @Override
            //what happens when the timer finishes
            public void onFinish() {
                mCountdown.setText("DONE!");
                progress++;
                mProgressBar.setProgress(100);
            }
        }.start();

        mEndConsultation = (Button) findViewById(R.id.endConsultation);
        mEndConsultation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
    Determine seconds to count down based on coding skills:
    Low: 5 minutes
    Moderate: 3 minutes
    High: 1.5 minutes
    Exceptional: 30 seconds
     */
    private long calculate() {
        long milis = 0;
        String skill = "";
        Intent intent = getIntent();
        if (intent.hasExtra("SKILL")) {
            skill = intent.getStringExtra("SKILL");
            switch (skill) {
                case "Low":
                    milis = 300000;
                    break;
                case "Moderate":
                    milis = 180000;
                    break;
                case "High":
                    milis = 90000;
                    break;
                case "Exceptional":
                    milis = 30000;
                    break;
                default:
                    break;
            }

        }
        return milis;
    }
}
