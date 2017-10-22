package com.example.xwc.tutorapp.Model;

/**
 * Created by Jacob and James on 15/10/2017.
 * Java Bean for Students
 */

public class Student {
    protected String mzID; //ZID of student
    protected String mFirstName; //Student first name
    protected String mSurname; //Student surname
    protected String mCodingSkill; //Coding skill of student
    protected String mClassID; //Enrolled class
    protected double mCurrentGrade; //student's current grade
    protected byte[] mPicture; //student's picture

    public Student(String mzID, String firstName, String surname, String codingSkill, String classID, double currentGrade, byte[] picture) {
        this.mzID = mzID;
        mFirstName = firstName;
        mSurname = surname;
        mCodingSkill = codingSkill;
        mClassID = classID;
        mCurrentGrade = currentGrade;
        mPicture = picture;
    }

    public Student() {
        this("","","","","",0.00,null);
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

    public byte[] getPicture() {
        return mPicture;
    }

    public void setPicture(byte[] picture) {
        mPicture = picture;
    }

    //Checks to see if Student objects are equal
    @Override
    public boolean equals(Object obj) {
        Student s = (Student) obj;
        return (s.getClassID().equals(this.getClassID()) &&
                s.getMzID().equals(this.getMzID()));
    }

}
