package com.example.qrgo;

//import android.content.Context;
//import android.location.Location;
//import android.location.LocationManager;

import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firestore.v1.FirestoreGrpc;

//import java.util.ArrayList;


/**
 * This class defines the QR
 */
public class QR {
    private String id;
    private String scannedBy;
    private Integer score;
    private String face;
    private Integer scannedAmnt;
    private FirebaseFirestore db;

    //TODO should comments be something that we add into the DB and not into the QR itself?

    /**
     * A QR constructor class that deals with taking a camera picture
     * @param id the ID/name of the qr
     * @param scannedBy the user who scanned it
     * @param score the score
     * @param face the face of the qr
     */
    public QR(String id, String scannedBy, Integer score, String face) {
        this.id = id;
        this.scannedBy = scannedBy;
        this.score = score;
        this.face = face;
        this.scannedAmnt = 1;
    }

    /**
     * A QR constructor that deals with more searching for QRs
     * @param id
     * @param face
     */
    public QR(String id, String face){
        this.id = id;
        this.face = face;
    }

    /**
     * returns the id of the QR
     * @return id set by class
     */
    public String getId() {return id;}

    /**
     * sets the name of the QR
     * @param id name set by user
     */
    public void setId(String id) {
        if (id.length() > 0){
            this.id = id;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * returns the user that scanned it, this will be updated in the list of users within the QRdb
     * @return user set by class
     */
    public String getScannedBy() {
        return scannedBy;
    }

    /**
     * provides a new scannedBy set by user
     * @param scannedBy a username
     */
    public void setScannedBy(String scannedBy) {
        if (scannedBy.length() > 0 ) {
            this.scannedBy = scannedBy;
        }
        // throw new IllegalArgumentException();
    }

    /**
     * provivdes the score of the QR
     * @return the score of the QR
     */
    public Integer getScore() {
        return score;
    }

    /**
     * sets a score for the QR
     * @param score user provides a score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * returns the face of the QR
     * @return qrs face
     */
    public String getFace() {
        return face;
    }

    /**
     * Sets the face of the qr
     * @param face which brings the face of the QR
     */
    public void setFace(String face) {
        this.face = face;
    }

    /**
     * @return amount of times QR has been scanned
     */
    public Integer getScannedAmnt() {
        return scannedAmnt;
    }

    /**
     * sets teh scanned amount of the specific QR
     * @param scannedAmnt
     */
    public void setScannedAmnt(Integer scannedAmnt) {
        this.scannedAmnt = scannedAmnt;
    }




}
