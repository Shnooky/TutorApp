package com.example.xwc.tutorapp.Model;

import java.util.Date;

/**
 * Created by Jacob on 15/10/2017.
 */

public class Tutorial {
    private String mTutorialId;
    private String mClassID;
    private Date mTutorialDate;
    private int mAbsentees;
    private int mLates;

    public Tutorial(String tutorialId, String classID, Date tutorialDate, int absentees, int lates) {
        mTutorialId = tutorialId;
        mClassID = classID;
        mTutorialDate = tutorialDate;
        mAbsentees = absentees;
        mLates = lates;
    }

    public Tutorial() {
        this("","",new Date(),0,0);
    }

    public String getTutorialId() {
        return mTutorialId;
    }

    public void setTutorialId(String tutorialId) {
        mTutorialId = tutorialId;
    }

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
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
}
