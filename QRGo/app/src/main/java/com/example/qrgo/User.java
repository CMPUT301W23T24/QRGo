package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
import java.util.List;

/**
 * Deals with the any user who opens up QRGo
 */
public class User extends AppCompatActivity {
    private final String TAG = "Hello";
    private final String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private List<String> scannedQRs;
    OnUserLoadedListener listener;


    /**
     * interface that when the user is loaded in will check the DB
     */
    // Define a callback interface for when the user data is loaded
    public interface OnUserLoadedListener {
        void onUserLoaded(User user);
    }

    /**
     * constructs the user class
     * @param deviceID provides the ID of the device
     */
    public User(String deviceID) {
        this.deviceID = deviceID;
        this.userName = "username";
        this.name = "name";
        this.email = "";
        this.phoneNum = 0;
        this.scannedQRs = new ArrayList<>();
    }

    /**
     * Creates a saved instance state
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getValuesFromDb(deviceID);
    }

    /**
     * gets the Id of the device
     * @return
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * gets the userName
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return gets the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the name of the suer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the phone number of the user
     */
    public Integer getPhoneNum() {
        return phoneNum;
    }

    /**
     *
     * @param phoneNum
     */
    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * connects to the DB of the user
     * @return CollectionReference
     */
    public CollectionReference connectToDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("user");
        return collectionReference;

    }

    /**
     * saves the user into the DB
     */
    public void saveUser() {
        CollectionReference collectionReference = connectToDB();
        HashMap<String, Object> playerData = new HashMap<>();
        playerData.put("name", this.name);
        playerData.put("username", this.userName);
        playerData.put("email", this.email);
        playerData.put("phoneNum", this.phoneNum);
        playerData.put("scannedQRs", this.scannedQRs);
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
                        Log.d(TAG, "Data couldn't be added" + e);
                    }
                });
    }

    /**
     * gets values from the Database
     * @param playerId
     * @param listener taken from the Interface
     */
    public void getValuesFromDb(String playerId, OnUserLoadedListener listener) {
        this.listener = listener;

        CollectionReference collectionReference = connectToDB();
        DocumentReference ref = collectionReference.document(playerId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        userName = (String) doc.get("username");
                        name = (String) doc.getData().get("name");
                        email = (String) doc.getData().get("email");
                        phoneNum = ((Long) doc.getData().get("phoneNum")).intValue();
                        scannedQRs = (List<String>) doc.getData().get("scannedQRs");
                        ;
                        Log.d("userId in user", playerId);
                        Log.d("userName in user", getUserName());
                        listener.onUserLoaded(User.this); // Invoke the callback function with the loaded user

                    } else {
                        saveUser();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    /**
     * Adds the qr into the user database
     * @param playerId ID of the plater/phonId
     * @param hash hash of the QR
     */
    public void addQR(String playerId, String hash) {
        CollectionReference collectionReference = connectToDB();
        if (!scannedQRs.contains(hash)) {
            this.scannedQRs.add(hash);
            DocumentReference ref = collectionReference.document(playerId);
            ref.update("scannedQRs", FieldValue.arrayUnion(hash));
        } else {
            Toast.makeText(User.this, "QR code already scanned", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * deletes the QR from the User DB
     * @param playerId ID of the player
     * @param hash hash content of the user
     */
    public void deleteQR(String playerId, String hash) {
        CollectionReference collectionReference = connectToDB();
        if (scannedQRs != null && scannedQRs.contains(hash)) {
            this.scannedQRs.remove(hash);
            DocumentReference ref = collectionReference.document(playerId);
            ref.update("scannedQRs", FieldValue.arrayRemove(hash));
        } else {
            Toast.makeText(User.this, "QR code already scanned", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * updates the collection reference using the device ID
     */
    public void updateDb(){
        CollectionReference collectionReference = connectToDB();
        collectionReference
                .document(this.deviceID)
                .update("username", this.userName);
        collectionReference
                .document(this.deviceID)
                .update("name", this.name);
        collectionReference
                .document(this.deviceID)
                .update("email", this.email);
        collectionReference
                .document(this.deviceID)
                .update("phoneNum", this.phoneNum);
    }

    /**
     *
     * the scanned QRs of the user
     */
    public List<String> getScannedQRs() {
        return this.scannedQRs;
    }

}
