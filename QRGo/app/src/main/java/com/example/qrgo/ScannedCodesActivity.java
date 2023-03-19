package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets the amount of scanned codes from the user
 */
public class ScannedCodesActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<QR> qrs;
    private ArrayAdapter<QR> qrAdapter;
    private String userId;
    private User user;
    private List<String> scannedCodes;
    ListView qrList;

    /**
     * creates the page for the QR scanned by the user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_codes);
        userId =  getIntent().getStringExtra("userId");


        qrList = findViewById(R.id.ScannedQRList);

        qrs = new ArrayList<QR>();
        qrAdapter = new QRListAdapter(ScannedCodesActivity.this, qrs);
        qrList.setAdapter(qrAdapter);


        db = FirebaseFirestore.getInstance();
        CollectionReference userCollectionReference = db.collection("user");
        CollectionReference qrCollectionReference = db.collection("qr");

        DocumentReference userRef = userCollectionReference.document(userId);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            /**
             * gets the scanned codes of the specific user
             * @param task provides the result from the DB
             */
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    scannedCodes = (List<String>) doc.getData().get("scannedQRs");

                    if (scannedCodes != null) {
                        for (String hash : scannedCodes) {
                            DocumentReference ref = qrCollectionReference.document(hash);
                            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        QR qr = new QR(hash, doc.get("id").toString(), doc.get("face").toString());
                                        qrAdapter.add(qr);
                                    } else {
                                        Log.d(TAG, "Failed with: ", task.getException());
                                    }
                                }
                            });
                            qrAdapter.notifyDataSetChanged();
                        }
                    }

                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        qrList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Implementing the removal of the QR code from the user QR list
            // Possibly implement throwing exceptions when it fails

            /**
             * When an item in the listview is long clicked it provides an option to delete the QR
             * @param adapterView
             * @param view
             * @param pos
             * @param l
             * @return Returns true to indicate that the deletion process was successful
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                DocumentReference qrRef = qrCollectionReference.document(qrs.get(pos).getHash());
                // Get the item at the long clicked position
                new AlertDialog.Builder(ScannedCodesActivity.this)
                        .setTitle("Do you want to remove the selected QR from the scanned list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 1. Remove the QR from the User scanned QR list (Done)
                                // 2. Remove the QR from the scanned codes (Done)
                                // 3. Remove the the user from the QR's scanned by list

                                // Removes the QR from the user's list of scanned QRs
                                userRef.update("scannedQRs", FieldValue.arrayRemove(qrs.get(pos).getHash()))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Successfully removed element from the array
                                                Log.d(TAG, "Element removed from array.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed to remove element from the array
                                                Log.e(TAG, "Error removing element from array.", e);
                                            }
                                        });
                                // Removes the user from the QR's list of users scanned
                                qrRef.update("scannedBy", FieldValue.arrayRemove(userId))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Successfully removed element from the array
                                                Log.d(TAG, "Element removed from array.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed to remove element from the array
                                                Log.e(TAG, "Error removing element from array.", e);
                                            }
                                        });

                                // Removes the QR from the Scanned Codes Activity
                                qrs.remove(pos);
                                qrAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                return true;

            }
        });


    }
}
