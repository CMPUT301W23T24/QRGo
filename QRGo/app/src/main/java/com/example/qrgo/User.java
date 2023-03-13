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
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
import java.util.List;

public class User extends AppCompatActivity {
    private final String TAG = "Hello";
    private final String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private List<String> scannedQRs;
    OnUserLoadedListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("user");

    // Define a callback interface for when the user data is loaded
    public interface OnUserLoadedListener {
        void onUserLoaded(User user);
    }

    public User(String deviceID) {
        this.deviceID = deviceID;
        this.userName = "username";
        this.name = "name";
        this.email = "";
        this.phoneNum = 0;
        this.scannedQRs = new ArrayList<>();

//        getValuesFromDb(deviceID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void saveUser() {
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

    public void getValuesFromDb(String playerId, OnUserLoadedListener listener) {
        this.listener = listener;

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

    public void addQR(String playerId, String hash) {
        if (!scannedQRs.contains(hash)) {
            this.scannedQRs.add(hash);
            DocumentReference ref = collectionReference.document(playerId);
            ref.update("scannedQRs", FieldValue.arrayUnion(hash));
        } else {
            Toast.makeText(User.this, "QR code already scanned", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteQR(String playerId, String hash) {
        if (scannedQRs != null && scannedQRs.contains(hash)) {
            this.scannedQRs.remove(hash);
            DocumentReference ref = collectionReference.document(playerId);
            ref.update("scannedQRs", FieldValue.arrayRemove(hash));
        } else {
            Toast.makeText(User.this, "QR code already scanned", Toast.LENGTH_SHORT).show();
        }
    }

    public List<String> getScannedQRs() {
        return this.scannedQRs;
    }

}
