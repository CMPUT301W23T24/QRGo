package com.example.qrgo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class TotalScoreBoard extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ArrayList<User> users;
    private ArrayAdapter<User> totalAdapter;
    private ListView userList;
    final String TAG = "Sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_scoreboard);
        // public List<String> sortTotal()

        users = new ArrayList<User>();
        totalAdapter = new TotalArrayAdapter( this, users);
        userList = findViewById(R.id.TotalScoreBoard);
        userList.setAdapter(totalAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference qrCollectionRef = db.collection("scoreboard_test");
        List<String> totalList = new ArrayList<>();

        qrCollectionRef.orderBy("totalScore", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot snapshot: task.getResult()){
                                User user= new User(snapshot.getId());
                                user.getValuesFromDb(snapshot.getId(), new User.OnUserLoadedListener() {
                                    @Override
                                    public void onUserLoaded(User user) {
                                    }
                                });
                                totalAdapter.add(user);
                                totalAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                })
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
////                            String userName = document.getString("userName");
////                            String score = document.getString("totalScore");
////                            int totalScore = Integer.parseInt("totalScore"); // Convert string to integer
////                            String qrString = userName + "*" + totalScore ;
////                            totalList.add(qrString);
//                            String deviceID = document.toString();
//                            Log.d("document", deviceID );
//                            users.add(new User(deviceID));
//                            totalAdapter.notifyDataSetChanged();
//                        }
//                        // Use the sorted QR list as needed
//                        // ...
//                    }
//                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting TotalScore: ", e);
                    }
                });


            // return totalList;


        }




}
