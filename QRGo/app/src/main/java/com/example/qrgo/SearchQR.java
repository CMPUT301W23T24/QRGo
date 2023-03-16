package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is the activity used to search for QR's
 */
public class SearchQR extends AppCompatActivity {
    private ArrayList<QR> findQuery;

    private ArrayAdapter<QR> qrAdapter;
    private EditText searchQRET;
    private Button qrSearchB;
    private FirebaseFirestore db;
    private ArrayList<QR> qrs;

    /**
    * Creates the Activity for the search function
    ** @param savedInstanceState remember where a user left off
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_qr);
        ListView qrList = (ListView) findViewById(R.id.qrSearchList);
        searchQRET = findViewById(R.id.searchQRET);
        qrSearchB = findViewById(R.id.searchQRB);


        db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("qr");

        qrSearchB.setOnClickListener(new View.OnClickListener() {
            /**
             * Uses the firestore DB to present any matching QR that the user searched for
             * @param view
             */
            @Override
            public void onClick(View view) {
                //upon every button click restart the adapter
                qrs = new ArrayList<QR>();
                qrAdapter = new QRListAdapter(SearchQR.this, qrs);
                qrList.setAdapter(qrAdapter);

                final String name = searchQRET.getText().toString();
                if (name.length() > 0){
                    cr.whereGreaterThanOrEqualTo("id", name)
                            .whereLessThanOrEqualTo("id",name + '\uf8ff' )
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                /**
                                 * if the Query actually completes the task present the ID and face of the QR in a ListView format
                                 * @param task holds all query information
                                 */
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){

                                        //for every result in the query paste it into the adapter
                                        for (DocumentSnapshot snapshot: task.getResult()){

                                            QR qr = new QR(snapshot.getId(), snapshot.get("id").toString(), snapshot.get("face").toString(), Integer.parseInt(snapshot.get("score").toString()));
                                            qrAdapter.add(qr);
                                            qrAdapter.notifyDataSetChanged();

                                        }
                                    }
                                }
                            });

                }
            }
        });

    }


}
