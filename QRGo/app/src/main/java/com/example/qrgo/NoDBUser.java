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

//    public NoDBUser(String deviceID) {
//        this.deviceID = deviceID;
//        this.userName = "username";
//        this.name = "name";
//        this.email = "";
//        this.phoneNum = 0;
//        this.scannedQRs = new ArrayList<>();
//
////        getValuesFromDb(deviceID);
//    }


    public NoDBUser(String deviceID) {
        this.deviceID = deviceID;
        this.userName = "";
        this.name = "";
        this.email = "";
        this.phoneNum = 0;

    }

    /**
     *
     * @return: returns the user's device id
     */
//    public String getDeviceID() {
//        return deviceID;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
    /**
     *
     * @return  returns the user's saved username
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Recieves an input for the requested user name and sets the device id's usename to the given parameter
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     *
     * @return
     * Returns the user's saved name
     */
    public String getName() {
        return name;
    }
    /**
     * Takes in a parameter called name and set's the user's name to the given parameter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     * @return
     * Returns the user's saved email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Takes in a parameter called email and set's the user's email to the given parameter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *
     * @return
     * Returns the user's saved phone number
     */
    public Integer getPhoneNum() {
        return phoneNum;
    }


    /**
     * Takes in a parameter called phoneNum and sets the user's phone number to the given parameter
     * @param phoneNum
     */
    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<String> getScannedQRs() {
        return this.scannedQRs;
    }

}
