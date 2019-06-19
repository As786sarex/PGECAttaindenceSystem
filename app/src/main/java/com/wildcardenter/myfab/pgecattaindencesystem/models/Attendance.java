package com.wildcardenter.myfab.pgecattaindencesystem.models;

import java.util.HashMap;

public class Attendance {
    private Object timestamp;
    private String date;
    private int noOfStudent;
    private HashMap<String,Resposnse> presentStudentList;

    public Attendance(Object timestamp, int noOfStudent, HashMap<String, Resposnse> presentStudentList,String date) {
        this.timestamp = timestamp;
        this.noOfStudent = noOfStudent;
        this.presentStudentList = presentStudentList;
        this.date=date;
    }

    public Attendance() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public int getNoOfStudent() {
        return noOfStudent;
    }

    public void setNoOfStudent(int noOfStudent) {
        this.noOfStudent = noOfStudent;
    }

    public HashMap<String, Resposnse> getPresentStudentList() {
        return presentStudentList;
    }

    public void setPresentStudentList(HashMap<String, Resposnse> presentStudentList) {
        this.presentStudentList = presentStudentList;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "timestamp=" + timestamp +
                ", noOfStudent=" + noOfStudent +
                ", presentStudentList=" + presentStudentList +
                '}';
    }
}
