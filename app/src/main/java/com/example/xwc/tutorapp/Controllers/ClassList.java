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

        ArrayList<Class> classes = loadDummyClasses();

        ListView classesListView = (ListView) findViewById(R.id.classlist);

        ClassAdapter adapter = new ClassAdapter(this, classes);

        classesListView.setAdapter(adapter);


    }

    private ArrayList<Class> loadDummyClasses() {
        ArrayList<Class> classes = new ArrayList<>();
        classes.add(new Class("M12A","Mon","1200","1400","CT","Quadrangle G021",22,0));
        classes.add(new Class("M12B","Mon","1200","1400","CT","Quadrangle G021",22,0));
        classes.add(new Class("M12C","Mon","1200","1400","CT","Quadrangle G021",22,0));
        return classes;
    }


}
