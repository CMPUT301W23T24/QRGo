package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
public class User {
    private final String TAG = "Hello";
    private String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    CollectionReference collectionReference= db.collection("user");
    public User(String deviceID) {
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
    public String getDeviceID() {
        return deviceID;
    }

    /**
     *
     * @return: returns the user's saved username
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
    /**
     * Takes the user's attributes and inputs them into the database
     */
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
    /**
     * Takes in a parameter called playerId and uses it to get the data of that user from the database
     * @param playerId
     */
    public void getValuesFromDb(String playerId){
        DocumentReference ref = collectionReference.document(playerId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        userName= (String) doc.getData().get("username");
                        name= (String) doc.getData().get("name");
                        email= (String) doc.getData().get("email");

                        phoneNum= ((Long) doc.getData().get("phoneNum")).intValue();

                    } else {
                        saveUser();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}
