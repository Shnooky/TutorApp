package com.example.xwc.tutorapp.Controllers;

import android.database.Cursor;
import android.widget.Toast;

import com.example.xwc.tutorapp.Database.DBOpenHelper;

/**
 * Created by jameszhang on 22/10/2017.
 */

public class CommonMethods {
    private static Toast prevToast = null;
    public static boolean hasTutorials(String classID) {
        Cursor c = DBOpenHelper.runSQL("select count(TUTORIAL_ID) as 'TOTAL' from TUTORIALS where CLASS = ?",
                new String[]{classID});
        if (c!= null && c.moveToNext()) {
            return (c.getInt(c.getColumnIndex("TOTAL")) > 0);
        }
        return false;
    }

    public static boolean hasStudents(String classID) {
        Cursor c = DBOpenHelper.runSQL("select count(ZID) as 'TOTAL' from STUDENTS where CLASS = ?",
                new String[]{classID});
        if (c!= null && c.moveToNext()) {
            return (c.getInt(c.getColumnIndex("TOTAL")) > 0);
        }
        return false;
    }

    public static void showToast(String msg) {
        // hide previous toast to avoid it overshadowing this one
        if (prevToast != null) {
            prevToast.cancel();
        }
        prevToast = Toast.makeText(MainActivity.appContext,msg,Toast.LENGTH_LONG);
        prevToast.show();
    }

    public static boolean studentExists(String zID) {
        Cursor c = DBOpenHelper.runSQL("select ZID from STUDENTS where ZID = ?",
                new String[]{zID});
        return (c != null && c.getCount() > 0);
    }
}
