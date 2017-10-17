package com.example.xwc.tutorapp.Model;

/**
 * Created by Jacob on 15/10/2017.
 */

public class StudentProfile {
    private String mzID;
    private String mFirstName;
    private String mSurname;
    private String mCodingSkill;
    private String mClassID;
    private double mCurrentGrade;
    private String mPicture;

    public StudentProfile(String mzID, String firstName, String surname, String codingSkill, String classID, double currentGrade, String picture) {
        this.mzID = mzID;
        mFirstName = firstName;
        mSurname = surname;
        mCodingSkill = codingSkill;
        mClassID = classID;
        mCurrentGrade = currentGrade;
        mPicture = picture;
    }

    public StudentProfile() {
        this("","","","","",0.00,"");
    }

    public String getMzID() {
        return mzID;
    }

    public void setMzID(String mzID) {
        this.mzID = mzID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getCodingSkill() {
        return mCodingSkill;
    }

    public void setCodingSkill(String codingSkill) {
        mCodingSkill = codingSkill;
    }

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
    }

    public double getCurrentGrade() {
        return mCurrentGrade;
    }

    public void setCurrentGrade(double currentGrade) {
        mCurrentGrade = currentGrade;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }


}
