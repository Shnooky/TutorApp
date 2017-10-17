package com.example.xwc.tutorapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacob on 16/10/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    // Class fields
    public static final String TABLE_CLASSES = "CLASSES";
    public static final String TABLE_STUDENTS = "STUDENTS";
    public static final String TABLE_TUTORIALS = "TUTORIALS";
    public static final String TABLE_STUDENT_TUTORIALS = "STUDENT_TUTORIALS";

    // Columns for each class
    public static final String CLASSES_CLASS_ID = "CLASS_ID";
    public static final String CLASSES_DAY = "DAY";
    public static final String CLASSES_STARTTIME = "STARTTIME";
    public static final String CLASSES_ENDTIME = "ENDTIME";
    public static final String CLASSES_TUTOR = "TUTOR";
    public static final String CLASSES_LOCATION = "LOCATION";
    public static final String[] CLASSES_ALL_COLUMNS =
            {CLASSES_CLASS_ID, CLASSES_DAY, CLASSES_STARTTIME, CLASSES_ENDTIME, CLASSES_TUTOR, CLASSES_LOCATION};

    public static final String STUDENTS_ZID = "ZID";
    public static final String STUDENTS_FIRSTNAME = "FIRSTNAME";
    public static final String STUDENTS_SURNAME = "SURNAME";
    public static final String STUDENTS_SKILL = "SKILL";
    public static final String STUDENTS_CLASS = "CLASS";
    public static final String STUDENTS_PICTURE = "PICTURE";
    public static final String[] STUDENTS_ALL_COLUMNS = {
            STUDENTS_ZID, STUDENTS_FIRSTNAME, STUDENTS_SURNAME, STUDENTS_SKILL, STUDENTS_CLASS, STUDENTS_PICTURE
    };

    public static final String TUTORIALS_ID = "TUTORIAL_ID";
    public static final String TUTORIALS_RAWID = "RAW_ID";
    public static final String TUTORIALS_DATE = "DATE";
    public static final String TUTORIALS_CLASS = "CLASS";
    public static final String[] TUTORIALS_ALL_COLUMNS = {
            TUTORIALS_ID, TUTORIALS_DATE, TUTORIALS_CLASS, TUTORIALS_RAWID
    };

    public static final String STUDENTS_TUTORIALS_TUTORIAL_ID = "TUTORIAL_ID";
    public static final String STUDENTS_TUTORIALS_ZID = "ZID";
    public static final String STUDENTS_TUTORIALS_LATE = "LATE";
    public static final String STUDENTS_TUTORIALS_ABSENT = "ABSENT";
    public static final String STUDENTS_TUTORIALS_MARK = "MARK";
    public static final String[] STUDENTS_TUTORIALS_ALL_COLUMNS = {
            STUDENTS_TUTORIALS_TUTORIAL_ID, STUDENTS_TUTORIALS_ZID, STUDENTS_TUTORIALS_LATE, STUDENTS_TUTORIALS_ABSENT, STUDENTS_TUTORIALS_MARK
    };

    // DB Info
    private static final String DATABASE_NAME = "tutor.db";
    private static final int DATABASE_VERSION = 2;

    private static final String CLASS_CREATE = "CREATE TABLE "+TABLE_CLASSES+" " +
            "("+ CLASSES_CLASS_ID +" TEXT, " +
            CLASSES_DAY + " TEXT, " +
            CLASSES_STARTTIME + " TEXT, " +
            CLASSES_ENDTIME + " TEXT, " +
            CLASSES_TUTOR + " TEXT, " +
            CLASSES_LOCATION + " TEXT);";

    private static final String DUMMY_CLASSES = "INSERT INTO "+TABLE_CLASSES+" (" +
            CLASSES_CLASS_ID+", "+CLASSES_DAY+", "+CLASSES_STARTTIME+", "+CLASSES_ENDTIME+", "+CLASSES_TUTOR+", "+CLASSES_LOCATION+") VALUES (" +
            "'W12A', 'Wed', '1200', '1400', 'TUTOR', 'ASB'), (" +
            "'W15A', 'Wed', '1500', '1700', 'TUTOR', 'QUAD')";

    private static final String STUDENT_CREATE = "CREATE TABLE "+TABLE_STUDENTS+" " +
            "(" + STUDENTS_ZID + " TEXT, " +
            STUDENTS_FIRSTNAME + " TEXT, " +
            STUDENTS_SURNAME + " TEXT, " +
            STUDENTS_SKILL + " TEXT, " +
            STUDENTS_CLASS + " TEXT, " +
            STUDENTS_PICTURE + " BLOB);";

    private static final String TUTORIAL_CREATE = "CREATE TABLE "+TABLE_TUTORIALS+" " +
            "(" + TUTORIALS_ID + " TEXT, " +
            TUTORIALS_RAWID + " INT, " +
            TUTORIALS_DATE + " TEXT, " +
            TUTORIALS_CLASS + " TEXT);";

    private static final String DUMMY_TUTORIALS = "INSERT INTO "+TABLE_TUTORIALS+" (" +
            TUTORIALS_ID+", "+TUTORIALS_DATE+", "+TUTORIALS_CLASS+") VALUES (" +
            "'Tutorial 3', '11/10/2017', 'W12A'), (" +
            "'Tutorial 2', '04/10/2017', 'W12A'), (" +
            "'Tutorial 1', '27/09/2017', 'W12A'), (" +
            "'Tutorial 3', '11/10/2017', 'W15A'), (" +
            "'Tutorial 2', '04/10/2017', 'W15A'), (" +
            "'Tutorial 1', '27/09/2017', 'W15A')";

    private static final String TUTORIAL_STUDENT_CREATE = "CREATE TABLE "+TABLE_STUDENT_TUTORIALS+" " +
            "("+STUDENTS_TUTORIALS_TUTORIAL_ID+" TEXT, " +
            STUDENTS_TUTORIALS_ZID + " TEXT, " +
            STUDENTS_TUTORIALS_LATE + " INT, " +
            STUDENTS_TUTORIALS_ABSENT + " INT, " +
            STUDENTS_TUTORIALS_MARK + " DOUBLE);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //need to check if database already exists, if it doesn't, then execute the following:
        db.execSQL(CLASS_CREATE);
        db.execSQL(DUMMY_CLASSES);
        db.execSQL(STUDENT_CREATE);
        db.execSQL(TUTORIAL_CREATE);
        db.execSQL(DUMMY_TUTORIALS);
        db.execSQL(TUTORIAL_STUDENT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        //db.execSQL("DROP TABLE IF EXISTS ");
        onCreate(db);
    }


}
