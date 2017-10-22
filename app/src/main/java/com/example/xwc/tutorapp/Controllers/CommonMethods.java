package com.example.xwc.tutorapp.Controllers;

import android.database.Cursor;
import android.widget.Toast;

import com.example.xwc.tutorapp.Database.DBOpenHelper;

/**
 * Created by Jacob and James on 22/10/2017.
 * A class dedicated to methods that are used frequently throughout the App. Allows for cleaner, less-cluttered code.
 * Mainly used for validation purposes, and disabling functionalities based on certain conditions (handled by the caller methods).
 */

public class CommonMethods {
    private static Toast prevToast = null;
    /*
    Checks if the class already has Tutorials, if it doesn't, the user should not be able to view the roll history.
     */
    public static boolean hasTutorials(String classID) {
        Cursor c = DBOpenHelper.runSQL("select count(TUTORIAL_ID) as 'TOTAL' from TUTORIALS where CLASS = ?",
                new String[]{classID});
        if (c!= null && c.moveToNext()) {
            return (c.getInt(c.getColumnIndex("TOTAL")) > 0);
        }
        return false;
    }

    /*
    Checks if a class has enrolled Students, if it doesn't, the user should not be able to start a new tutorial.
     */
    public static boolean hasStudents(String classID) {
        Cursor c = DBOpenHelper.runSQL("select count(ZID) as 'TOTAL' from STUDENTS where CLASS = ?",
                new String[]{classID});
        if (c!= null && c.moveToNext()) {
            return (c.getInt(c.getColumnIndex("TOTAL")) > 0);
        }
        return false;
    }

    //"Toast handling"
    public static void showToast(String msg) {
        // hide previous toast to avoid it overshadowing this one
        if (prevToast != null) {
            prevToast.cancel();
        }
        prevToast = Toast.makeText(MainActivity.appContext,msg,Toast.LENGTH_LONG);
        prevToast.show();
    }

    /*
    When searching for a student, check if the student exists, if it doesn't there will be no result.
     Also used to prevent users from creating a student with the same ZID.
     */
    public static boolean studentExists(String zID) {
        Cursor c = DBOpenHelper.runSQL("select ZID from STUDENTS where ZID = ?",
                new String[]{zID});
        return (c != null && c.getCount() > 0);
    }
}
