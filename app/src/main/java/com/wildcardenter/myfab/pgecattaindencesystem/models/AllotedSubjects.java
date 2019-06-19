package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class AllotedSubjects {
    private String subjectName;
    private String subjectCode;
    private String forSem;
    private String dept;
    private String teacherUid;

    public AllotedSubjects(String subjectName, String subjectCode, String forSem, String dept,String teacherUid) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.forSem = forSem;
        this.dept = dept;
        this.teacherUid=teacherUid;
    }

    public AllotedSubjects() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getForSem() {
        return forSem;
    }

    public void setForSem(String forSem) {
        this.forSem = forSem;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTeacherUid() {
        return teacherUid;
    }

    public void setTeacherUid(String teacherUid) {
        this.teacherUid = teacherUid;
    }

    @Override
    public String toString() {
        return "AllotedSubjects{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", forSem='" + forSem + '\'' +
                ", dept='" + dept + '\'' +
                ", teacherUid='" + teacherUid + '\'' +
                '}';
    }
}
