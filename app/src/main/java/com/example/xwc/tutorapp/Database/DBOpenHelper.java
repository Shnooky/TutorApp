package com.example.xwc.tutorapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacob on 16/10/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tutor.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CLASS_CREATE = "CREATE TABLE CLASSES " +
            "(CLASS_ID TEXT, " +
            "DAY TEXT, " +
            "STARTTIME TEXT, " +
            "ENDTIME TEXT, " +
            "TUTOR TEXT, " +
            "LOCATION TEXT);";

    private static final String STUDENT_CREATE = "CREATE TABLE STUDENTS " +
            "(ZID TEXT, " +
            "FIRSTNAME TEXT, " +
            "SURNAME TEXT, " +
            "SKILL TEXT, " +
            "GRADE DOUBLE, " +
            "CLASS TEXT, " +
            "PICTURE TEXT);";

    private static final String TUTORIAL_CREATE = "CREATE TABLE TUTORIALS " +
            "(TUTORIAL_ID TEXT, " +
            "DATE TEXT, " +
            "ABSENT INT, " +
            "CLASS TEXT, " +
            "LATE INT);";

    private static final String TUTORIAL_STUDENT_CREATE = "CREATE TABLE TUTORIALS " +
            "(TUTORIAL_ID TEXT, " +
            "ZID TEXT, " +
            "MARK DOUBLE);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //need to check if database already exists, if it doesn't, then execute the following:
        db.execSQL(CLASS_CREATE);
        db.execSQL(STUDENT_CREATE);
        db.execSQL(TUTORIAL_CREATE);
        db.execSQL(TUTORIAL_STUDENT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("DROP TABLE IF EXISTS ");
        onCreate(db);
    }


}
