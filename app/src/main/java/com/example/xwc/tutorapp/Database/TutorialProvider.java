package com.example.xwc.tutorapp.Database;

/**
 * Created by Jacob and James on 17/10/2017.
 * ContentProvider for Tutorial Table in SQLite Database
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TutorialProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.xwc.tutorapp.tutorialProvider";
    private static final String BASE_PATH = "tutorials";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(DBOpenHelper.TABLE_TUTORIALS, DBOpenHelper.TUTORIALS_ALL_COLUMNS,
                selection, selectionArgs, null, null,
                DBOpenHelper.TUTORIALS_ID + " DESC");
    }



    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_TUTORIALS,
                null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_TUTORIALS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_TUTORIALS,
                values, selection, selectionArgs);
    }
}
