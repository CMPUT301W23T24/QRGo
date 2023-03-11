package com.example.qrgo;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
public class User {
    FirebaseFirestore db;
    CollectionReference contact_info = db.collection("contact_info");
    Map<String, Object> data1 = new HashMap<>();
    private String email;
    private String name;
    private String phone_number;
    private ArrayList<QR> QRList = new ArrayList<QR>;

    public User(String email, String name, String phone_number) {
        this.email = email;
        this.name = name;
        this.phone_number = phone_number;
        data1.put("name", name);
        data1.put("email", email);
        data1.put("phone_number", phone_number);
        contact_info.document("1234").set(data1);
    }

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
