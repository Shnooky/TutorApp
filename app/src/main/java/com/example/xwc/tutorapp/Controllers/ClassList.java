package com.example.xwc.tutorapp.Controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
        FloatingActionButton add_class_button = (FloatingActionButton) findViewById(R.id.add_class_button);

        // Set events for UI elements
        add_class_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ClassList.this, ClassAdder.class);
                startActivity(intent);
            }
        });

        classesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Class c = classes.get(position);
                Intent i = new Intent(getBaseContext(),ClassAdder.class);
                i.putExtra("CLASSID", c.getClassId());
                i.putExtra("DAY", c.getDay());
                i.putExtra("STARTTIME", c.getStartTime());
                i.putExtra("ENDTIME", c.getEndTime());
                i.putExtra("LOCATION", c.getLocation());

                startActivity(i);
            }
        });
    }

    private void loadClasses() {
        Cursor c = getContentResolver().query(ClassProvider.CONTENT_URI,
                DBOpenHelper.CLASSES_ALL_COLUMNS, null,null, null);
        classes.clear();
        while (c!= null && c.moveToNext()) {
            classes.add(new Class(c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_CLASS_ID)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_DAY)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_STARTTIME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_ENDTIME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_TUTOR)),
                    c.getString(c.getColumnIndex(DBOpenHelper.CLASSES_LOCATION)),
                    22,
                    0));
        }

        // Fill list
        ClassAdapter adapter = new ClassAdapter(this, classes);
        classesListView.setAdapter(adapter);
    }
}
