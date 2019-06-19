package com.wildcardenter.myfab.pgecattaindencesystem.models;

import java.util.HashMap;

public class Teacher {
    private String name;
    private String uid;
    private String email;
    private boolean isVerified;
    private String accessCode;
    private HashMap<String,AllotedSubjects> allotedSubjects;

    public Teacher(String name, String uid, String email,
                   boolean isVerified, String accessCode, HashMap<String, AllotedSubjects> allotedSubjects) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.isVerified = isVerified;
        this.accessCode = accessCode;
        this.allotedSubjects = allotedSubjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        isVerified = isVerified;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public HashMap<String, AllotedSubjects> getAllotedSubjects() {
        return allotedSubjects;
    }

    public void setAllotedSubjects(HashMap<String, AllotedSubjects> allotedSubjects) {
        this.allotedSubjects = allotedSubjects;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", isPending=" + isVerified +
                ", accessCode='" + accessCode + '\'' +
                ", allotedSubjects=" + allotedSubjects +
                '}';
    }
}
