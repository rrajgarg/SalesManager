package com.example.android.salesmanager;

/**
 * Created by raj garg on 10-05-2019.
 */

public class Profile {
    String name;
    String id;
    String email;
    boolean admin;

    public Profile(String name, String id, String email, boolean admin) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.admin = admin;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
