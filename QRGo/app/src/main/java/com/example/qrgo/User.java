package com.example.qrgo;

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

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

/**
 * This class is responsible for the information of the user
 * as well as the lookup of other users
 */
public class User extends AppCompatActivity {
    private final String TAG = "Hello";
    private final String deviceID;
    private String userName;
    private String name;
    private String email;
    private Integer phoneNum;
    private List<String> scannedQRs;
    private Integer totalScore;
    private Integer maxQRScore;
    private Integer oldMaxQR;
    private String maxQRName;
    private String oldMaxQRName;
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
     *
     * @param deviceID provides the ID of the device
     */
    public User(String deviceID) {
        this.deviceID = deviceID;
        this.userName = "username";
        this.name = "name";
        this.email = "";
        this.phoneNum = 0;
        this.scannedQRs = new ArrayList<>();
        this.totalScore = 0;
        this.maxQRScore = 0;
        this.oldMaxQR = 0;
        this.maxQRName = "";
        this.oldMaxQRName = "";
    }

    public User(String deviceID, String username, Integer totalScore) {
        this.deviceID = deviceID;
        this.userName = username;
        this.totalScore = totalScore;
    }

    public User(String deviceID, String username, String maxQRName, Integer maxQRScore) {
        this.deviceID = deviceID;
        this.userName = username;
        this.maxQRScore = maxQRScore;
        this.maxQRName = maxQRName;
    }

    public String getMaxQRName() {
        return maxQRName;
    }

    public void setMaxQRName(String maxQRName) {
        this.maxQRName = maxQRName;
    }

    public Integer getMaxQRScore() {
        return maxQRScore;
    }

    public void setMaxQRScore(Integer maxQRScore) {
        this.maxQRScore = maxQRScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Creates a saved instance state
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * gets the Id of the device
     *
     * @return
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * gets the userName
     *
     * @return Returns the user's saved username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Recieves an input for the requested user name and sets the device id's usename to the given parameter
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Returns the user's saved name
     */
    public String getName() {
        return name;
    }

    /**
     * Takes in a parameter called name and set's the user's name to the given parameter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the user's saved email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Takes in a parameter called email and set's the user's email to the given parameter
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the user's saved phone number
     */
    public Integer getPhoneNum() {
        return phoneNum;
    }

    /**
     * Takes in a parameter called phoneNum and sets the user's phone number to the given parameter
     *
     * @param phoneNum
     */
    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * connects to the DB of the user
     *
     * @return CollectionReference
     */
    public CollectionReference connectToDB() {
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
        playerData.put("totalScore", this.totalScore);
        playerData.put("maxQRScore", this.maxQRScore);
        playerData.put("oldMaxQR", this.oldMaxQR);
        playerData.put("maxQRName", this.maxQRName);
        playerData.put("oldMaxQRName", this.oldMaxQRName);

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
     * Takes in a parameter called playerId and uses it to get the data of that user from the database
     *
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
                        totalScore = ((Long) doc.getData().get("totalScore")).intValue();
                        maxQRScore = ((Long) doc.getData().get("maxQRScore")).intValue();
                        maxQRName = (String) doc.getData().get("maxQRName");

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
     *
     * @param playerId ID of the plater/phonId
     * @param hash     hash of the QR
     */
    public void addQR(String playerId, String hash, Integer score, String qr_name) {
        Log.d("HASH", hash);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference qrRef = db.collection("qr").document(hash);
        DocumentReference userRef = db.collection("user").document(playerId);

        qrRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int scannedAmnt = document.getLong("scannedAmnt").intValue();
                        if (!scannedQRs.contains(hash)) {
                            updateTotalScore(score);
                        }
                        userRef.update("scannedQRs", FieldValue.arrayUnion(hash));

                        if (maxQRScore < score) {
                            oldMaxQR = maxQRScore;
                            setMaxQRScore(score);
                            setMaxQRName(qr_name);

                        }
                        updateDb();
                    }
                }
            }
        });
    }



    /**
     * deletes the QR from the User DB
     *
     * @param playerId ID of the player
     * @param hash     hash content of the user
     */
    public void deleteQR(String playerId, String hash, Integer score) {
        CollectionReference collectionReference = connectToDB();
        if (scannedQRs != null && scannedQRs.contains(hash)) {
            this.scannedQRs.remove(hash);
            DocumentReference ref = collectionReference.document(playerId);
            ref.update("scannedQRs", FieldValue.arrayRemove(hash));
            updateTotalScore(-score);
            if (this.maxQRScore == score) {
                setMaxQRName(this.oldMaxQRName);
                setMaxQRScore(this.oldMaxQR);
            }
        } else {
            //Toast.makeText(User.this, "QR code already scanned", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * updates the collection reference using the device ID
     */
    public void updateDb() {
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
        collectionReference
                .document(this.deviceID)
                .update("totalScore", this.totalScore);
        collectionReference
                .document(this.deviceID)
                .update("maxQRName", this.maxQRName);
        collectionReference
                .document(this.deviceID)
                .update("maxQRScore", this.maxQRScore);
        collectionReference.document(this.deviceID)
                .update("maxQRScore", this.maxQRScore);
    }

    /**
     * the scanned QRs of the user
     */
    public List<String> getScannedQRs() {
        return this.scannedQRs;
    }

    public void addScannedQRs(String qr) {
        this.scannedQRs.add(qr);
    }

    public void updateTotalScore(int i) {
        this.totalScore += i;
    }


}
