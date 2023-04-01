package com.example.qrgo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Provides the list of scanned users by the QR
 */
public class ScannedDoop extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ScannedArrayAdapter scannedAdapter;
    private ListView userList;
    private String hash;
    final String TAG = "Sample";
    FirebaseFirestore db;

    /**
     * Creates te view for the users who scanned the QR
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_list);

        hash = getIntent().getStringExtra("hash");


        dataList = new ArrayList<String>();
        scannedAdapter = new ScannedArrayAdapter(this, dataList);
        userList = findViewById(R.id.scanned_users_list);
        userList.setAdapter(scannedAdapter);

        // Reference: https://firebase.google.com/docs/firestore/query-data/get-data#get_a_document
        // To read from a firestore database
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("qr");
        if (hash != null) {
            collectionReference.document(hash)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        /**
                         * deals with who scanned the query
                         *
                         * @param task provides the result from the query
                         */
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    Log.d(TAG, document.get("scannedBy").toString());
                                    ArrayList<String> scannedUsers = (ArrayList<String>) document.get("scannedBy");
                                    for (int i = 0; i < scannedUsers.size(); i++) {
                                        scannedAdapter.add(scannedUsers.get(i));
                                        //dataList.add(scannedUsers.get(i));
                                        scannedAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }

    }

}
