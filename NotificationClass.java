package com.example.android.salesmanager;

/**
 * Created by raj garg on 11-05-2019.
 */

public class NotificationClass {
    String text;
    String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotificationClass() {
    }

    public NotificationClass(String text, String time) {
        this.text = text;
        this.time = time;
    }
}
