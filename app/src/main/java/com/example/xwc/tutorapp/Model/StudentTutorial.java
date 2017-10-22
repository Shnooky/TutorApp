package com.example.xwc.tutorapp.Model;

import android.content.Intent;
import android.database.Cursor;

import com.example.xwc.tutorapp.Controllers.*;
import com.example.xwc.tutorapp.Controllers.StudentProfile;
import com.example.xwc.tutorapp.Database.DBOpenHelper;
import com.example.xwc.tutorapp.Database.StudentProvider;
import android.content.ContentResolver;
/**
 * Created by Jacob on 15/10/2017.
 */

public class StudentTutorial {
    private String mzID;
    private String firstName;
    private String lastName;
    private String mTutorialID;
    private boolean mLate;
    private boolean mAbsent;
    private double mMark;
    private int mParticipation;

    public byte[] getmImg() {
        return mImg;
    }

    public void setmImg(byte[] mImg) {
        this.mImg = mImg;
    }

    private byte[] mImg;

    public StudentTutorial(String mzID, String firstName, String lastName, String mTutorialID, boolean mLate, boolean mAbsent, double mMark, int participation, byte[] img) {
        this.mzID = mzID;
        this.mTutorialID = mTutorialID;
        this.mLate = mLate;
        this.mAbsent = mAbsent;
        this.mMark = mMark;
        this.mParticipation = participation;
        this.firstName= firstName;
        this.lastName = lastName;
        this.mImg = img;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentTutorial() {
        this("","","","", false, false,0,0, null);
    }

    public String getMzID() {
        return mzID;
    }

    public void setMzID(String mzID) {
        this.mzID = mzID;
    }

    public String getmTutorialID() {
        return mTutorialID;
    }

    public void setmTutorialID(String mTutorialID) {
        this.mTutorialID = mTutorialID;
    }

    public boolean ismLate() {
        return mLate;
    }

    public void setmLate(boolean mLate) {
        this.mLate = mLate;
    }

    public boolean ismAbsent() {
        return mAbsent;
    }

    public void setmAbsent(boolean mAbsent) {
        this.mAbsent = mAbsent;
    }

    public double getmMark() {
        return mMark;
    }

    public void setmMark(double mMark) {
        this.mMark = mMark;
    }

    public int getParticipation() {
        return mParticipation;
    }

    public void setParticipation(int participation) {
        mParticipation = participation;
    }
}
