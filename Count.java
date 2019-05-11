package com.example.android.salesmanager;

/**
 * Created by raj garg on 11-05-2019.
 */

public class Count {
    int notifications;
    int globalMessages;

    public Count() {
    }

    public Count(int notifications, int globalMessages) {
        this.notifications = notifications;
        this.globalMessages = globalMessages;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public int getGlobalMessages() {
        return globalMessages;
    }

    public void setGlobalMessages(int globalMessages) {
        this.globalMessages = globalMessages;
    }
}
