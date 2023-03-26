package com.example.qrgo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TotalScoreBoard extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ArrayList<User> users;
    private TotalArrayAdapter totalAdapter;
    private ListView userList;
    final String TAG = "Sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_scoreboard);


    }

    public List<String> sortTotal() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference qrCollectionRef = db.collection("user");
        List<String> totalList = new ArrayList<>();

        qrCollectionRef.orderBy("totalScore", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String userName = document.getString("userName");
                            String score = document.getString("totalScore");
                            int totalScore = Integer.parseInt("totalScore"); // Convert string to integer
                            String qrString = userName + "*" + totalScore ;
                            totalList.add(qrString);
                        }
                        // Use the sorted QR list as needed
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting TotalScore: ", e);
                    }
                });


        return totalList;


    }
}
