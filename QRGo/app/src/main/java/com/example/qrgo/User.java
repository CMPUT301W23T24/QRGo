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

}
