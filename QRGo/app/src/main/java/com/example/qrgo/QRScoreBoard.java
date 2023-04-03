package com.example.qrgo;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class QRScoreBoard extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ArrayList<User> users;
    private ArrayAdapter<User> qrScoreAdapter;
    private ListView userList;
    final String TAG = "Sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highest_qr_scoreboard);

        users = new ArrayList<User>();
        qrScoreAdapter = new QRScoreArrayAdapter( this, users);
        userList = findViewById(R.id.HighestQRScoreBoard);
        userList.setAdapter(qrScoreAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference qrCollectionRef = db.collection("user");
        List<String> qrScoreList = new ArrayList<>();

        qrCollectionRef.orderBy("maxQRScore", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot snapshot: task.getResult()){
                                User user= new User(snapshot.getId(), snapshot.get("username").toString(), snapshot.get("maxQRName").toString(), Integer.parseInt(snapshot.get("maxQRScore").toString()));
                                // Log.d("YO", user.getUserName() + " => " + user.getTotalScore());
                                qrScoreAdapter.add(user);
                                qrScoreAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting max QR Score: ", e);
                    }
                });

    }


}



