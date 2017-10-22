package com.example.xwc.tutorapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.example.xwc.tutorapp.Controllers.StudentProfile;
import com.example.xwc.tutorapp.R;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import com.example.xwc.tutorapp.Controllers.MainActivity;

/**
 * Created by Jacob and James on 16/10/2017.
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
    public static final String STUDENTS_TUTORIALS_PARTICIPATION = "PARTICIPATION";
    public static final String[] STUDENTS_TUTORIALS_ALL_COLUMNS = {
            STUDENTS_TUTORIALS_TUTORIAL_ID, STUDENTS_TUTORIALS_ZID, STUDENTS_TUTORIALS_LATE, STUDENTS_TUTORIALS_ABSENT, STUDENTS_TUTORIALS_MARK, STUDENTS_TUTORIALS_PARTICIPATION
    };

    // DB Info
    private static final String DATABASE_NAME = "tutor.db";
    private static final int DATABASE_VERSION = 2;

    //Classes table creation
    private static final String CLASS_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_CLASSES+" " +
            "("+ CLASSES_CLASS_ID +" TEXT, " +
            CLASSES_DAY + " TEXT, " +
            CLASSES_STARTTIME + " TEXT, " +
            CLASSES_ENDTIME + " TEXT, " +
            CLASSES_TUTOR + " TEXT, " +
            CLASSES_LOCATION + " TEXT);";

    //Dummy data for Classes
    private static final String DUMMY_CLASSES = "INSERT INTO "+TABLE_CLASSES+" (" +
            CLASSES_CLASS_ID+", "+CLASSES_DAY+", "+CLASSES_STARTTIME+", "+CLASSES_ENDTIME+", "+CLASSES_TUTOR+", "+CLASSES_LOCATION+") VALUES (" +
            "'W12A', 'Wed', '1200', '1400', 'TUTOR', 'ASB'), (" +
            "'W15A', 'Wed', '1500', '1700', 'TUTOR', 'QUAD')";


    //Students table creation
    private static final String STUDENT_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_STUDENTS+" " +
            "(" + STUDENTS_ZID + " TEXT, " +
            STUDENTS_FIRSTNAME + " TEXT, " +
            STUDENTS_SURNAME + " TEXT, " +
            STUDENTS_SKILL + " TEXT, " +
            STUDENTS_CLASS + " TEXT, " +
            STUDENTS_PICTURE + " BLOB);";


    //Dummy data for students
    private static final String DUMMY_STUDENTS = "INSERT INTO "+TABLE_STUDENTS+" (" +
            STUDENTS_ZID+", "+STUDENTS_FIRSTNAME+", "+STUDENTS_SURNAME+", "+STUDENTS_SKILL+", "+STUDENTS_CLASS+", "+STUDENTS_PICTURE+") VALUES (" +
            "'Z5019998', 'Jacob', 'Meyerowitz','Exceptional','W12A','???'), (" +
            "'Z1234567', 'Bob', 'Smith','High','W12A','???'), (" +
            "'Z7654321', 'Michael', 'C.','Moderate','W12A','???'), (" +
            "'Z5014884', 'James', 'Zhang','Exceptional','W15A','???'), (" +
            "'Z1111111', 'Johnny', 'Bravo','Low','W15A','???'), (" +
            "'Z9999999', 'Neil', 'Harris','Moderate','W15A','???'), (" +
            "'Z5014883', 'Yenni', 'Tim','Exceptional','W12A','???'), (" +
            "'Z5014885', 'Yeye', 'Jiang','High','W15A','???')";


    //Tutorial table creation
    private static final String TUTORIAL_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_TUTORIALS+" " +
            "(" + TUTORIALS_ID + " TEXT, " +
            TUTORIALS_RAWID + " INT, " +
            TUTORIALS_DATE + " TEXT, " +
            TUTORIALS_CLASS + " TEXT);";

    //Dummy data for tutorials
    private static final String DUMMY_TUTORIALS = "INSERT INTO "+TABLE_TUTORIALS+" (" +
            TUTORIALS_ID+", "+TUTORIALS_DATE+", "+TUTORIALS_CLASS+") VALUES (" +
            "'Tutorial 3', '11/10/2017', 'W12A'), (" +
            "'Tutorial 2', '04/10/2017', 'W12A'), (" +
            "'Tutorial 1', '27/09/2017', 'W12A'), (" +
            "'Tutorial 3', '11/10/2017', 'W15A'), (" +
            "'Tutorial 2', '04/10/2017', 'W15A'), (" +
            "'Tutorial 1', '27/09/2017', 'W15A')";


    /*Bridging entity (between Tutorial and Students) represents many-to-many relationship between Students and Tutorials
    i.e. many students attend many tutorials.
     */
    private static final String TUTORIAL_STUDENT_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_STUDENT_TUTORIALS+" " +
            "("+STUDENTS_TUTORIALS_TUTORIAL_ID+" TEXT, " +
            STUDENTS_TUTORIALS_ZID + " TEXT, " +
            STUDENTS_TUTORIALS_LATE + " INT, " +
            STUDENTS_TUTORIALS_ABSENT + " INT, " +
            STUDENTS_TUTORIALS_MARK + " DOUBLE, " +
            STUDENTS_TUTORIALS_PARTICIPATION+" INT);";

    //Dummy data for briding entity
    private static final String DUMMY_TUTORIAL_STUDENTS = "INSERT INTO "+TABLE_STUDENT_TUTORIALS+" (" +
            STUDENTS_TUTORIALS_TUTORIAL_ID+", "+STUDENTS_TUTORIALS_ZID+", "+STUDENTS_TUTORIALS_LATE+", "+STUDENTS_TUTORIALS_ABSENT+", "+
            STUDENTS_TUTORIALS_MARK+", "+STUDENTS_TUTORIALS_PARTICIPATION+") VALUES (" +
            "'Tutorial 1', 'Z5019998',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z1234567',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z7654321',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z5014883',0,0,2.0,0), (" +
            "'Tutorial 2', 'Z5019998',0,0,2.0,0), (" +
            "'Tutorial 2', 'Z1234567',1,0,2.0,0), (" +
            "'Tutorial 2', 'Z7654321',0,1,0.0,0), (" +
            "'Tutorial 2', 'Z5014883',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z5019998',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z1234567',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z7654321',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z5014883',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z5014884',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z1111111',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z9999999',0,0,2.0,0), (" +
            "'Tutorial 1', 'Z5014885',0,0,2.0,0), (" +
            "'Tutorial 2', 'Z5014884',0,0,2.0,0), (" +
            "'Tutorial 2', 'Z1111111',1,0,2.0,0), (" +
            "'Tutorial 2', 'Z9999999',0,1,0.0,0), (" +
            "'Tutorial 2', 'Z5014885',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z5014884',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z1111111',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z5014885',0,0,2.0,0), (" +
            "'Tutorial 3', 'Z9999999',0,0,2.0,0)";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CLASS_CREATE);
        db.execSQL(STUDENT_CREATE);
        db.execSQL(TUTORIAL_STUDENT_CREATE);
        db.execSQL(TUTORIAL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_TUTORIALS);
        onCreate(db);
    }

    //Should only be called on requested (linked to a TextView on MainActivity)
    public void createDummyData(SQLiteDatabase db) {
        onUpgrade(db,1,1);
        db.execSQL(DUMMY_CLASSES);
        db.execSQL(DUMMY_STUDENTS);
        db.execSQL(DUMMY_TUTORIAL_STUDENTS);
        db.execSQL(DUMMY_TUTORIALS);

        // Insert photos
        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_yenni)),
                        "Z5014883"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_james)),
                        "Z5014884"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_yeye)),
                        "Z5014885"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_adam)),
                        "Z5019998"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_michael)),
                        "Z7654321"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_johnny)),
                        "Z1111111"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_neil)),
                        "Z9999999"});

        db.execSQL("update STUDENTS set PICTURE = ? WHERE ZID = ?",
                new Object[]{StudentProfile.getBytes(BitmapFactory.decodeResource(MainActivity.appContext.getResources(), R.drawable.sample_bob)),
                        "Z1234567"});


    }

    //Can be called and used to run raw SQL queries and surpass ContentProvider limitations.
    public static Cursor runSQL(String sql, String[] args) {
        DBOpenHelper helper = new DBOpenHelper(MainActivity.appContext);
        SQLiteDatabase database = helper.getWritableDatabase();
        return database.rawQuery(sql, args);
    }

}
