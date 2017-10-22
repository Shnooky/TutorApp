package com.example.xwc.tutorapp.Model;

import java.util.ArrayList;

/**
 * Created by Jacob and James on 15/10/2017.
 * Java Bean for Tutorials
 */
public class Tutorial {
    private String mTutorialId; //Tutorial ID (Tutorial 1, Tutorial 2, etc.)
    private String mTutorialDate; //Date of tutorial - Date manipulation is not used, therefore suitable to store as a String.
    private int mAbsentees; //Number of absent students
    private int mLates; //Number of late students

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
}
