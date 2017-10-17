package com.example.xwc.tutorapp.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jacob on 15/10/2017.
 */

public class Tutorial {
    private String mTutorialId;
    private String mTutorialDate;
    private int mAbsentees;
    private int mLates;
    private ArrayList<StudentProfile> mStudentProfiles;

    public Tutorial(String tutorialId, String tutorialDate, int absentees, int lates) {
        mTutorialId = tutorialId;
        mTutorialDate = tutorialDate;
        mAbsentees = absentees;
        mLates = lates;
    }

    public Tutorial() {
        this("","",0,0);
    }

    public String getTutorialId() {
        return mTutorialId;
    }

    public void setTutorialId(String tutorialId) {
        mTutorialId = tutorialId;
    }

    public String getTutorialDate() {
        return mTutorialDate;
    }

    public void setTutorialDate(String tutorialDate) {
        mTutorialDate = tutorialDate;
    }

    public int getAbsentees() {
        return mAbsentees;
    }

    public void setAbsentees(int absentees) {
        mAbsentees = absentees;
    }

    public int getLates() {
        return mLates;
    }

    public void setLates(int lates) {
        mLates = lates;
    }

    public ArrayList<StudentProfile> getStudentProfiles() {
        return mStudentProfiles;
    }

    public void setStudentProfiles(ArrayList<StudentProfile> studentProfiles) {
        this.mStudentProfiles = studentProfiles;
    }
}
