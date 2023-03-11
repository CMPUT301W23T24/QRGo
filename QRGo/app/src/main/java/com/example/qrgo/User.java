package com.example.qrgo;

import java.util.List;

public class User {
    private String deviceID;
    private String userName;
    private String name;
    private String email;

    public User(String deviceID, String userName, String name, String email) {
        this.deviceID = deviceID;
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
