package com.example.qrgo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
import java.util.List;

public class User {
    private final String TAG = "Hello";
    private String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    CollectionReference collectionReference= db.collection("user");
    public User(String deviceID, Context context) {
        this.deviceID = deviceID;
        this.userName = "";
        this.name = "";
        this.email = "";
        this.phoneNum = 0;
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

    public void saveUser(){
        HashMap<String, Object> playerData = new HashMap<>();
        playerData.put("name", this.name);
        playerData.put("username", this.userName);
        playerData.put("email", this.email);
        playerData.put("phoneNum", this.phoneNum);
        collectionReference.document(deviceID)
                .set(playerData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Data has been added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Data couldn't be added"+ e);
                    }
                });
    }

    public void getValuesFromDb(String playerId){
        collectionReference.document(playerId).get().addOnCompleteListener(docTask ->{
            DocumentSnapshot doc= docTask.getResult();
            this.userName= (String) doc.getData().get("username");
            this.name= (String) doc.getData().get("name");
            this.email= (String) doc.getData().get("email");
            this.phoneNum= (Integer) doc.getData().get("phoneNum");
        });
    }
}
