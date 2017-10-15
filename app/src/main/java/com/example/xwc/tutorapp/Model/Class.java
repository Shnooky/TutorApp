package com.example.xwc.tutorapp.Model;

/**
 * Created by Jacob on 15/10/2017.
 */

public class Class {
    private String mClassId;
    private String mDay;
    private String mStartTime;
    private String mEndTime;
    private String mTutor;
    private String mLocation;
    private int mNumStu;
    private double mAverageGrade;

    public Class(String classId, String day, String startTime, String endTime, String tutor, String location, int numStu, double averageGrade) {
        mClassId = classId;
        mDay = day;
        mStartTime = startTime;
        mEndTime = endTime;
        mTutor = tutor;
        mLocation = location;
        mNumStu = numStu;
        mAverageGrade = averageGrade;
    }

    public Class() {
        this("","","","","","",0,0.0);
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
}
