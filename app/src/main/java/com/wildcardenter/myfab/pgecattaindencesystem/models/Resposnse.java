package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class Resposnse {
    private String roll_no;
    private String name;
    private String uid;
    private int starCount;
    private long timestamp;

    public Resposnse(String roll_no, String uid, int starCount, long timestamp,String name) {
        this.roll_no = roll_no;
        this.uid = uid;
        this.starCount = starCount;
        this.timestamp = timestamp;
        this.name=name;
    }

    public Resposnse() {
    }

    public String getRoll_no() {
        return roll_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Resposnse{" +
                "roll_no='" + roll_no + '\'' +
                ", uid='" + uid + '\'' +
                ", starCount=" + starCount +
                ", timestamp=" + timestamp +
                '}';
    }
}
