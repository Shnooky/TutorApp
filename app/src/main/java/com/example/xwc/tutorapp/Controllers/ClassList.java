package com.example.xwc.tutorapp.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 15/10/2017.
 */

public class ClassList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_activity);

        ArrayList<Class> classes = new ArrayList<Class>();

        ListView classesListView = (ListView) findViewById(R.id.list);

        ClassAdapter adapter = new ClassAdapter(this, classes);

        classesListView.setAdapter(adapter);


    }




}
