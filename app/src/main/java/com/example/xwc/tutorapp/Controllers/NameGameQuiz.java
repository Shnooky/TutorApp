package com.example.xwc.tutorapp.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.xwc.tutorapp.Adapters.TutorialStudentAdapter;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import com.example.xwc.tutorapp.Model.Student;
import com.example.xwc.tutorapp.Database.TutorialProvider;
import com.example.xwc.tutorapp.Model.Class;

import com.example.xwc.tutorapp.Model.StudentTutorial;
import com.example.xwc.tutorapp.Model.Tutorial;
import com.example.xwc.tutorapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/*
Created by: Jacob and James on 20/10/2017
Activity that handles the Name Game quiz
 */
public class NameGameQuiz extends AppCompatActivity implements View.OnClickListener {

    // UI variables
    private ImageView imgStudent;
    private Button btnOption1;
    private Button btnOption2;
    private Button btnOption3;
    private Button btnOption4;
    private Button btnNext;

    // Variables for Quiz Session
    private ArrayList<Student> studentsList;
    private int totalScore = 0;
    private int currentQIndex = 0;
    private final int numQs = 10;
    private Student currStudent;
    ArrayList<String> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namegame_quiz_activity);
// Grab references to UI elements
        btnOption1 = (Button) findViewById(R.id.btnNGOption1);
        btnOption2 = (Button) findViewById(R.id.btnNGOption2);
        btnOption3 = (Button) findViewById(R.id.btnNGOption3);
        btnOption4 = (Button) findViewById(R.id.btnNGOption4);
        btnNext = (Button) findViewById(R.id.btnNGNextQ);
        imgStudent = (ImageView) findViewById(R.id.imgNGStudent);

        // Set button events
        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent.hasExtra("ALLCLASSES")) {
            // Free for all
            studentsList = getStudentsList("*");
        } else {
            //Class-specific
            String classID = intent.getStringExtra("CLASSID");
            studentsList = getStudentsList(classID);
        }

        /*
        Prevents the game from starting if there are not enough students enrolled in the class (less than 4)
         */
        if (studentsList == null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Name Game");
            builder1.setMessage("You must have at least four students to play the name game");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            btnOption1.setVisibility(View.INVISIBLE);
            btnOption2.setVisibility(View.INVISIBLE);
            btnOption3.setVisibility(View.INVISIBLE);
            btnOption4.setVisibility(View.INVISIBLE);
            alert11.show();
            return;
        }

        // Randomise questions
        Collections.shuffle(studentsList);

        loadNextQ();
    }


    private void loadNextQ() {
        if (currentQIndex >= studentsList.size()) {
            // Quiz ended
            Intent i = new Intent(this, NameGameScore.class);
            i.putExtra("SCORE", totalScore);
            i.putExtra("TOTAL", studentsList.size());
            startActivity(i);
            finish();
            return;
        }

        // Otherwise load next question
        currStudent = studentsList.get(currentQIndex);

        imgStudent.setImageBitmap(StudentProfile.getImage(currStudent.getPicture()));

        // Get some random names
        ArrayList<Student> tempStudents = new ArrayList<>(studentsList);

        // Remove the current student to generate a list of wrong answers
        for (int i = 0; i < tempStudents.size(); i++) {
            if (currStudent.getMzID().equals(tempStudents.get(i).getMzID())) {
                tempStudents.remove(i);
            }
        }
        // Shuffle the incorrect answers
        Collections.shuffle(tempStudents);

        // Finally, pick the first three an generate the answer set
        answers.clear();
        answers.add(tempStudents.get(0).getFirstName() + " " + tempStudents.get(0).getSurname());
        answers.add(tempStudents.get(1).getFirstName() + " " + tempStudents.get(1).getSurname());
        answers.add(tempStudents.get(2).getFirstName() + " " + tempStudents.get(2).getSurname());
        answers.add(currStudent.getFirstName() + " " + currStudent.getSurname());

        // Shuffle the correct answers
        Collections.shuffle(answers);

        // Display options
        btnOption1.setText(answers.get(0));
        btnOption2.setText(answers.get(1));
        btnOption3.setText(answers.get(2));
        btnOption4.setText(answers.get(3));

        btnNext.setVisibility(View.INVISIBLE);
        currentQIndex++;
        restoreAllButtons();
    }

    /*
    Determine if answer is correct
     */
    private boolean correctAnswer(int ansIdx) {
        return answers.get(ansIdx).equals(currStudent.getFirstName() + " " + currStudent.getSurname());
    }

    /*
    Mark the answer as correct (green)
     */
    private void markAsCorrect(int id) {
        ((Button) findViewById(id)).setBackgroundResource(R.drawable.correct_answer);
    }

    /*
    Mark the answer as incorrect (red)
    */
    private void markAsIncorrect(int id) {
        ((Button) findViewById(id)).setBackgroundResource(R.drawable.incorrect_answer);
    }

    private void restoreAllButtons() {
        ((Button) findViewById(R.id.btnNGOption1)).setBackgroundResource(R.drawable.regular_answer);
        ((Button) findViewById(R.id.btnNGOption2)).setBackgroundResource(R.drawable.regular_answer);
        ((Button) findViewById(R.id.btnNGOption3)).setBackgroundResource(R.drawable.regular_answer);
        ((Button) findViewById(R.id.btnNGOption4)).setBackgroundResource(R.drawable.regular_answer);
    }

    @Override
    public void onClick(View v) {
        int answerIndex = 0;

        switch (v.getId()) {
            case R.id.btnNGOption1:
                answerIndex = 0;
                break;
            case R.id.btnNGOption2:
                answerIndex = 1;
                break;
            case R.id.btnNGOption3:
                answerIndex = 2;
                break;
            case R.id.btnNGOption4:
                answerIndex = 3;
                break;
            case R.id.btnNGNextQ:
                loadNextQ();
                return;
            default:
        }

        if (correctAnswer(answerIndex)) {
            // Correct
            markAsCorrect(v.getId());
            totalScore++;
        } else {
            // Incorrect
            markAsIncorrect(v.getId());
            // Highlight correct answer(s)
            if (correctAnswer(0)) {
                markAsCorrect(R.id.btnNGOption1);
            } else if (correctAnswer(1)) {
                markAsCorrect(R.id.btnNGOption2);
            } else if (correctAnswer(2)) {
                markAsCorrect(R.id.btnNGOption3);
            } else if (correctAnswer(3)) {
                markAsCorrect(R.id.btnNGOption4);
            }

        }
        btnNext.setVisibility(View.VISIBLE);
    }

    /*
    Get the list of Students - logic handles whether we are getting students from one class or combining
    a list from all classes.
     */
    private ArrayList<Student> getStudentsList(String classID) {
        ArrayList<Student> students = new ArrayList<>();

        Cursor c;
        // Check if we want ALL students, or just one class...
        if (classID.equals("*")) {
            c = getContentResolver().query(StudentProvider.CONTENT_URI,
                    DBOpenHelper.STUDENTS_ALL_COLUMNS,
                    null,
                    null, null);
        } else {
            c = getContentResolver().query(StudentProvider.CONTENT_URI,
                    DBOpenHelper.STUDENTS_ALL_COLUMNS,
                    DBOpenHelper.STUDENTS_CLASS + " = ?",
                    new String[]{classID}, null);
        }

        if (c.getCount() < 4) {
            return null;
        }

        students.clear();

        while (c != null && c.moveToNext()) {
            students.add(new Student(c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_ZID)),
                    c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_FIRSTNAME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SURNAME)),
                    c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_SKILL)),
                    c.getString(c.getColumnIndex(DBOpenHelper.STUDENTS_CLASS)),
                    0,
                    c.getBlob(c.getColumnIndex(DBOpenHelper.STUDENTS_PICTURE))
            ));
        }

        return students;
    }
}