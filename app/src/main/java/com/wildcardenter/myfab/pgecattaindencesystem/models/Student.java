package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class Student {
    private String name;
    private String rollno;
    private String email;
    private boolean isVerified;
    private int accessCode;

    public Student(String name, String rollno, boolean isVerified,String email,int accessCode) {
        this.name = name;
        this.rollno = rollno;
        this.isVerified = isVerified;
        this.email=email;
        this.accessCode=accessCode;
    }

    public Student() {
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollno='" + rollno + '\'' +
                ", email='" + email + '\'' +
                ", isPending=" + isVerified +
                ", accessCode=" + accessCode +
                '}';
    }
}
