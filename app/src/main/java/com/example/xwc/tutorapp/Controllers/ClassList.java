package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;

import java.util.ArrayList;

/**
 * Created by Jacob on 15/10/2017.
 */

public class ClassList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_activity);

        ArrayList<Class> classes = new ArrayList<Class>();

        ListView classesListView = (ListView) findViewById(R.id.classlist);

        ClassAdapter adapter = new ClassAdapter(this, classes);

        classesListView.setAdapter(adapter);


    }




}
