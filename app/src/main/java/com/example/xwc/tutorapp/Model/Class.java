package com.example.xwc.tutorapp.Model;

/**
 * Created by Jacob and James on 15/10/2017.
 * Java bean for Classes
 */

public class Class {
    private String mClassId; //Section ID
    private String mDay; //Day of class
    private String mStartTime; //Class starttime
    private String mEndTime; //Class end time
    private String mTutor; //TODO: delete this
    private String mLocation; //Location of class
    private int mNumStu; //Number of students in the class
    private double mAverageGrade; //Average grade of class
    private double mPart; //TODO: delete this

    public Class(String classId, String day, String startTime, String endTime, String tutor, String location, int numStu, double averageGrade,
                 double part) {
        mClassId = classId;
        mDay = day;
        mStartTime = startTime;
        mEndTime = endTime;
        mTutor = tutor;
        mLocation = location;
        mNumStu = numStu;
        mAverageGrade = averageGrade;
        mPart = part;
    }

    public Class() {
        this("","","","","","",0,0.0, 0);
    }

    public String getClassId() {
        return mClassId;
    }

    public void setClassId(String classId) {
        mClassId = classId;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getTutor() {
        return mTutor;
    }

    public void setTutor(String tutor) {
        mTutor = tutor;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public int getNumStu() {
        return mNumStu;
    }

    public void setNumStu(int numStu) {
        mNumStu = numStu;
    }

    public double getAverageGrade() {
        return mAverageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        mAverageGrade = averageGrade;
    }

    public void setParticipation(double part) {
        mPart = part;
    }

    public double getParticipation() {
        return mPart;
    }
}
