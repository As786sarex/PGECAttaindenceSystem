package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class Subject {
    private String teacherUid;
    private String dept;
    private String subjectCode;

    public Subject(String teacherUid, String dept, String subjectCode) {
        this.teacherUid = teacherUid;
        this.dept = dept;
        this.subjectCode = subjectCode;
    }

    public Subject() {
    }

    public String getTeacherUid() {
        return teacherUid;
    }

    public void setTeacherUid(String teacherUid) {
        this.teacherUid = teacherUid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "teacherUid='" + teacherUid + '\'' +
                ", dept='" + dept + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                '}';
    }
}
