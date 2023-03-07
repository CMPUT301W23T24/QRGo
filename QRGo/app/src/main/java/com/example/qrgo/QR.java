package com.example.qrgo;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.firebase.firestore.FirebaseFirestore;

public class QR {
    private String id;
    private String scannedBy;
    private Integer score;
    private String face;
    private Integer scannedAmnt;
    private FirebaseFirestore db;
    public QR(String id, String scannedBy, Integer score, String face) {
        this.id = id;
        this.scannedBy = scannedBy;
        this.score = score;
        this.face = face;
        this.scannedAmnt = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScannedBy() {
        return scannedBy;
    }

    public void setScannedBy(String scannedBy) {
        this.scannedBy = scannedBy;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getScannedAmnt() {
        return scannedAmnt;
    }

    public void setScannedAmnt(Integer scannedAmnt) {
        this.scannedAmnt = scannedAmnt;
    }

    //TODO figure out the collections to add and remove
    public void addToDB(){
        //get collection and add the necessary hash, score, face etc

    }
    public void removeFromDB(){
        //

    }
}
