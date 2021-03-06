package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.xwc.tutorapp.R;
import android.widget.*;


/*
Created by: Jacob and James on 20/10/2017
Main menu for Name Game
 */
public class NameGameManager extends AppCompatActivity implements View.OnClickListener {
    private Button btnByClass;
    private Button btnAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namegame_manager);
        // Grab references to UI elements
        btnByClass = (Button) findViewById(R.id.btnNGByClass);
        btnAll = (Button) findViewById(R.id.btnNGFreeStyle);
        //Set on click listeners
        btnByClass.setOnClickListener(this);
        btnAll.setOnClickListener(this);
    }

    /*
   Set OnClickListener's for Name Game options
    */
    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnNGByClass:
                intent = new Intent(this, ClassList.class);
                intent.putExtra("NAMEGAME", "TRUE");
                break;
            case R.id.btnNGFreeStyle:
                intent = new Intent(this, NameGameQuiz.class);
                intent.putExtra("ALLCLASSES", "TRUE");
                break;
            default:
        }

        if (intent != null) {
            startActivity(intent);
        }
    }



}