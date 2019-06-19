package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class Admin {
    private String name;
    private String uid;
    private String email;
    private boolean isPending;
    private String accessCode;

    public Admin(String name, String uid, String email, boolean isPending, String accessCode) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.isPending = isPending;
        this.accessCode = accessCode;
    }

    public Admin() {
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

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", isPending=" + isPending +
                ", accessCode='" + accessCode + '\'' +
                '}';
    }
}
