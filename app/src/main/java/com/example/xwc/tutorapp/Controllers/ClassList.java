package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;
import android.content.Intent;
import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.R;
import android.view.View;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Jacob, but really created by James on 15/10/2017.
 */

public class ClassList extends AppCompatActivity {
    private FloatingActionButton add_class_button;
    ArrayList<Class> classes = new ArrayList<>();
    ListView classesListView;

    @Override
    public void onResume(){
        super.onResume();
        loadClasses();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_activity);

        // Grab references to UI elements
        classesListView = (ListView) findViewById(R.id.classlist);
        add_class_button = (FloatingActionButton) findViewById(R.id.add_class_button);

        // Set events for UI elements
        add_class_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ClassList.this, ClassAdder.class);
                startActivity(intent);
            }
        });

        // Check whether the parent activity is name game or class menu
        Intent i = getIntent();
        if (i.hasExtra("NAMEGAME")) {
            // Make listview click redirect to name quiz game
            classesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Class c = classes.get(position);
                    Intent i = new Intent(getBaseContext(), NameGameQuiz.class);
                    i.putExtra("CLASSID", c.getClassId());
                    startActivity(i);
                    finish();
                }
            });
        } else {
            // Make listview click redirect to class/tutorial menu
            classesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Class c = classes.get(position);
                    Intent i = new Intent(getBaseContext(),ClassMenu.class);
                    i.putExtra("CLASSID", c.getClassId());
                    i.putExtra("DAY", c.getDay());
                    i.putExtra("STARTTIME", c.getStartTime());
                    i.putExtra("ENDTIME", c.getEndTime());
                    i.putExtra("LOCATION", c.getLocation());
                    i.putExtra("PART", c.getParticipation());
                    startActivity(i);
                }
            });
        }
        loadClasses();
    }

    private void loadClasses() {
        Log.d("","Load classes");
        Cursor c = DBOpenHelper.runSQL("select CLASSES.*, COUNT(DISTINCT STUDENTS.ZID) as 'TOTAL', " +
                "AVG(STUDENT_TUTORIALS.MARK) AS 'AVGMARK', AVG(STUDENT_TUTORIALS.PARTICIPATION)" +
                " AS 'PART' from CLASSES LEFT OUTER JOIN STUDENTS" +
                " ON (CLASSES.CLASS_ID = STUDENTS.CLASS) LEFT OUTER JOIN STUDENT_TUTORIALS ON " +
                "(STUDENTS.ZID = STUDENT_TUTORIALS.ZID) GROUP BY CLASSES.CLASS_ID", null);

        classes.clear();
        while (c!= null && c.moveToNext()) {
            Log.d("","Getting class from database");
            classes.add(new Class(c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_DAY)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_STARTTIME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_ENDTIME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_TUTOR)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_LOCATION)),
                    c.getInt(c.getColumnIndex("TOTAL")),
                    c.getDouble(c.getColumnIndex("AVGMARK")),
                    c.getInt(c.getColumnIndex("PART"))));
        }

        // Fill list
        ClassAdapter adapter = new ClassAdapter(this, classes);
        classesListView.setAdapter(adapter);
    }
}
