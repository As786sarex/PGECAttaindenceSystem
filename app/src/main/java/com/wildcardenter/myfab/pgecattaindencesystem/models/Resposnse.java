package com.wildcardenter.myfab.pgecattaindencesystem.models;

public class Resposnse {
    private String roll_no;
    private String uid;
    private long timestamp;

    public Resposnse(String roll_no, String uid, long timestamp) {
        this.roll_no = roll_no;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public Resposnse() {
    }

    public String getRoll_no() {
        return roll_no;
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
                ", timestamp=" + timestamp +
                '}';
    }
}
