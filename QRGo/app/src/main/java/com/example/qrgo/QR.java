package com.example.qrgo;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.ArrayList;



public class QR {
    private String id;
    private ArrayList<String> scannedBy;
    private ArrayList<String> comments;
    private Integer score;
    private String face;
    private Integer scannedAmnt;
    private FirebaseFirestore db;
    public QR(String id, ArrayList<String> scannedBy, ArrayList<String> comments, Integer score, String face) {
        this.id = id;
        this.scannedBy = scannedBy;
        this.comments = comments;
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

    public ArrayList<String> getScannedBy() {
        return scannedBy;
    }


    public void setScannedBy(ArrayList<String> scannedBy) {
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


    public ArrayList<String> getComments() {return comments;}

    public void setComments(ArrayList<String> comments){ this.comments = comments;}
    //TODO figure out the collections to add and remove

}
