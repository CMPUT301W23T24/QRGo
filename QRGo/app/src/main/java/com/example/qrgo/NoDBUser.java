package com.example.qrgo;

import java.util.ArrayList;
import java.util.List;


//THIS IS USED FOR TEST CASES ONLY DO NOT USE IT FOR ANYTHING ELSE UNLESS SPECIFIED
public class NoDBUser {

    private final String TAG = "Hello";
    private final String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private List<String> scannedQRs;

    public NoDBUser(String deviceID) {
        this.deviceID = deviceID;
        this.userName = "username";
        this.name = "name";
        this.email = "";
        this.phoneNum = 0;
        this.scannedQRs = new ArrayList<>();

//        getValuesFromDb(deviceID);
    }

    public String getDeviceID() {
        return deviceID;
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

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<String> getScannedQRs() {
        return this.scannedQRs;
    }
}
