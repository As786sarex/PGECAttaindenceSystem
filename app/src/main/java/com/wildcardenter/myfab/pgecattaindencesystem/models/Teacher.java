package com.wildcardenter.myfab.pgecattaindencesystem.models;

import java.util.HashMap;

public class Teacher {
    private String name;
    private String email;
    private boolean isVerified;
    private String accessCode;
    private HashMap<String,String> allottedSubjects;

    public Teacher() {
    }

    public Teacher(String name, String email,
                   boolean isVerified, String accessCode, HashMap<String, String> allottedSubjects) {
        this.name = name;
        this.email = email;
        this.isVerified = isVerified;
        this.accessCode = accessCode;
        this.allottedSubjects = allottedSubjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public HashMap<String, String> getAllottedSubjects() {
        return allottedSubjects;
    }

    public void setAllottedSubjects(HashMap<String, String> allottedSubjects) {
        this.allottedSubjects = allottedSubjects;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isPending=" + isVerified +
                ", accessCode='" + accessCode + '\'' +
                ", allottedSubjects=" + allottedSubjects +
                '}';
    }
}
