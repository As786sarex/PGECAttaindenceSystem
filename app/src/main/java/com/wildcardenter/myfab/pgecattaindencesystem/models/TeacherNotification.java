package com.wildcardenter.myfab.pgecattaindencesystem.models;

/*
    Class On Package com.wildcardenter.myfab.pgecattaindencesystem.models
    
    Created by Asif Mondal on 05-07-2019 at 20:18
*/


public class TeacherNotification {
    private String title;
    private String body;
    private String date;

    public TeacherNotification() {
    }

    public TeacherNotification(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public TeacherNotification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }
}
