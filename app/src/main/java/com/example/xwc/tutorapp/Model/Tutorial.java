package com.example.xwc.tutorapp.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jacob on 15/10/2017.
 */

public class Tutorial {
    private String mTutorialId;
    private Date mTutorialDate;
    private int mAbsentees;
    private int mLates;
    private ArrayList<StudentProfile> mStudentProfiles;

    public Tutorial(String tutorialId, Date tutorialDate, int absentees, int lates) {
        mTutorialId = tutorialId;
        mTutorialDate = tutorialDate;
        mAbsentees = absentees;
        mLates = lates;
    }

    public Tutorial() {
        this("",new Date(),0,0);
    }

    public String getTutorialId() {
        return mTutorialId;
    }

    public void setTutorialId(String tutorialId) {
        mTutorialId = tutorialId;
    }

    public Date getTutorialDate() {
        return mTutorialDate;
    }

    public void setTutorialDate(Date tutorialDate) {
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
