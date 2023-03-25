package com.example.qrgo;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class QRScoreBoard {
    //private List <User> userList;
    //private List
    //private Dictionary<String, Integer> qrDict = new Hashtable<>();

    public List<String> sortQR(){
            // int i = 0;
            // int total_users = 0;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference qrCollectionRef = db.collection("qr");
        List<String> qrList = new ArrayList<>();

        qrCollectionRef.orderBy("score", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String scannedBy = document.getString("scannedBy");
                            String id = document.getString("id");
                            String scoreString = document.getString("score");
                            int score = Integer.parseInt(scoreString); // Convert string to integer
                            String qrString = scannedBy + "*" + id + "*" + scoreString;
                            qrList.add(qrString);
                        }
                        // Use the sorted QR list as needed
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting QR: ", e);
                    }
                });



        return qrList;

    }

}
