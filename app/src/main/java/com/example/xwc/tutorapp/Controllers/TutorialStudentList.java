package com.example.xwc.tutorapp.Controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;
import android.content.Intent;
import android.graphics.BitmapFactory;
import com.example.xwc.tutorapp.Adapters.ClassAdapter;
import com.example.xwc.tutorapp.Adapters.TutorialStudentAdapter;
import com.example.xwc.tutorapp.Database.ClassProvider;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Database.StudentTutorialProvider;
import com.example.xwc.tutorapp.Database.TutorialProvider;
import com.example.xwc.tutorapp.Model.Class;
import com.example.xwc.tutorapp.Model.StudentTutorial;
import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import android.view.View;
import android.database.Cursor;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Jacob, but really created by James on 15/10/2017.
 */

public class TutorialStudentList extends AppCompatActivity {
    private FloatingActionButton delete_tutorial_button;
    ArrayList<StudentTutorial> students = new ArrayList<>();
    ListView studentsLV;
    private String currClass = null;
    private String currTutorialID = null;
    private String currentClass = null;
    private TutorialStudentAdapter adapter = null;

    @Override
    public void onResume() {
        super.onResume();
        students.clear();
        adapter.clear();
        if (currTutorialID == null && currClass == null) {
            return;
        }
        loadStudents();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        students.clear();
        adapter.clear();
        if (currTutorialID == null && currClass == null) {
            return;
        }
        loadStudents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_students_activity);

        // Grab references to UI elements
        studentsLV = (ListView) findViewById(R.id.studentsList);

        // Set events for UI elements

        studentsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                StudentTutorial s = students.get(position);
                Intent i = new Intent(getBaseContext(), TutorialStudentEditor.class);
                i.putExtra("TUTORIALID", s.getmTutorialID());
                i.putExtra("ZID", s.getMzID());
                i.putExtra("LATE", s.ismLate() ? 1 : 0);
                i.putExtra("GRADE", Double.toString(s.getmMark()));
                i.putExtra("ABSENT", s.ismAbsent() ? 1 : 0);
                i.putExtra("NAME", s.getFirstName() + " " + s.getLastName());
                i.putExtra("PART",Integer.toString(s.getParticipation()));
                startActivity(i);
            }
        });

        delete_tutorial_button = (FloatingActionButton) findViewById(R.id.delete_tutorial_button);


        Intent i = getIntent();
        currClass = i.getStringExtra("CLASSID");
        if (i.hasExtra("TUTORIALID")) {
            // We're displaying a historical recordset
            currTutorialID = i.getStringExtra("TUTORIALID");
        } else {
            // Generate new Tutorial ID
            DBOpenHelper helper = new DBOpenHelper(getBaseContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            Cursor c = database.rawQuery("select count(TUTORIAL_ID) from TUTORIALS WHERE CLASS = ?", new String[]{
                    currClass
            });

            if (c != null && c.moveToNext()) {
                // get new RAW ID
                int rawID = c.getInt(0);
                String tutorialDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                String tutorialID = "Tutorial " + (rawID + 1); //+ " @ " + tutorialDate;
                // Create new tutorials entry
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBOpenHelper.TUTORIALS_CLASS, currClass);
                insertValues.put(DBOpenHelper.TUTORIALS_DATE, tutorialDate);
                insertValues.put(DBOpenHelper.TUTORIALS_ID, tutorialID);
                insertValues.put(DBOpenHelper.TUTORIALS_RAWID, rawID + 1);
                getContentResolver().insert(TutorialProvider.CONTENT_URI, insertValues);

                // Create new tutorial-student recordset
                c = getContentResolver().query(StudentProvider.CONTENT_URI,
                        DBOpenHelper.STUDENTS_ALL_COLUMNS,
                        DBOpenHelper.STUDENTS_CLASS + " IN (SELECT " + DBOpenHelper.CLASSES_CLASS_ID + " from " +
                                DBOpenHelper.TABLE_CLASSES + " where " + DBOpenHelper.CLASSES_CLASS_ID + " = ?)",
                        new String[]{currClass}, null);

                while (c != null && c.moveToNext()) {
                    insertValues = new ContentValues();
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_ZID, c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_ZID)));
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_ABSENT, 0);
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_LATE, 0);
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_MARK, 0);
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID, tutorialID);
                    insertValues.put(DBOpenHelper.STUDENTS_TUTORIALS_PARTICIPATION, 0);
                    getContentResolver().insert(StudentTutorialProvider.CONTENT_URI, insertValues);
                }
                currTutorialID = tutorialID;
            }
        }
        loadStudents();

        delete_tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class returnClass = new Class();
                DBOpenHelper helper = new DBOpenHelper(getBaseContext());
                SQLiteDatabase database = helper.getWritableDatabase();
                getContentResolver().delete(TutorialProvider.CONTENT_URI, DBOpenHelper.TUTORIALS_ID + " = ?" + " AND " + DBOpenHelper.TUTORIALS_CLASS + " = ?",
                        new String[]{currTutorialID, currentClass});
                database.execSQL("DELETE FROM STUDENT_TUTORIALS WHERE TUTORIAL_ID = ? AND ZID IN (SELECT ZID FROM STUDENTS WHERE CLASS = ?)", new String[]{currTutorialID, currentClass});
                finish();
            }

        });
    }

    private void loadStudents() {
        Intent intent = getIntent();
        if (intent.hasExtra("TUTORIAL_CLASS")) {
            currentClass = intent.getStringExtra("TUTORIAL_CLASS");
        } else {
            currentClass = currClass;
        }

        Cursor c = getContentResolver().query(StudentTutorialProvider.CONTENT_URI,
                DBOpenHelper.STUDENTS_TUTORIALS_ALL_COLUMNS,
                DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID + " = ?",
                new String[]{currTutorialID}, null);

        students.clear();
        int count = 0;
        while (c != null && c.moveToNext()) {
            //Log.d("XWCABC", c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID))+":" + count++);
            Cursor c2 = getContentResolver().query(StudentProvider.CONTENT_URI,
                    DBOpenHelper.STUDENTS_ALL_COLUMNS,
                    "ZID = ? AND CLASS = ?",
                    new String[]{c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ZID)), currentClass}, null);
            if (c2 != null && c2.moveToNext()) {

                String studentFirstName = c2.getString(c2.getColumnIndex(DBOpenHelper.STUDENTS_FIRSTNAME));
                String studentLastName = c2.getString(c2.getColumnIndex(DBOpenHelper.STUDENTS_SURNAME));
                byte[] studentImg = c2.getBlob(c2.getColumnIndex(DBOpenHelper.STUDENTS_PICTURE));

                students.add(new StudentTutorial(c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ZID)),
                        studentFirstName,
                        studentLastName,
                        c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_TUTORIAL_ID)),
                        c.getInt(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_LATE))==1,
                        c.getInt(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_ABSENT))==1,
                        c.getDouble(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_MARK)),
                        c.getInt(c.getColumnIndex(DBOpenHelper.STUDENTS_TUTORIALS_PARTICIPATION)),
                        studentImg
                ));
            }
        }

        // Fill list
        adapter = new TutorialStudentAdapter(this, students);
        studentsLV.setAdapter(adapter);
    }
}
