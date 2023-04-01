package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
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
    private Integer totalScore;
    private Integer totalScanned;
    private Integer highestScore;
    private Integer lowestScore;
    ListView qrList;
    TextView highScore;
    TextView lowScore;
    TextView totalQRScore;
    TextView totalQRamount;


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

        highScore = findViewById(R.id.HighestVal);
        lowScore = findViewById(R.id.LowestVal);
        totalQRScore = findViewById(R.id.totalVal);
        totalQRamount = findViewById(R.id.TotalScanned);


        totalScore = 0;
        totalScanned = 0;
        highestScore = 0;

        lowestScore = Integer.MAX_VALUE;


        db = FirebaseFirestore.getInstance();
        CollectionReference userCollectionReference = db.collection("user");
        CollectionReference qrCollectionReference = db.collection("qr");


        if (userId != null) {


            DocumentReference userRef = userCollectionReference.document(userId);

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                /**
                 * gets the scanned codes of the specific user
                 *
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
                                            QR qr = new QR(hash, doc.get("id").toString(), doc.get("face").toString(), Integer.parseInt(doc.get("score").toString()));
                                            qrAdapter.add(qr);


                                            Integer val = Integer.parseInt(doc.get("score").toString());
                                            if (val > highestScore) {
                                                highestScore = val;
                                            }
                                            if (val < lowestScore) {
                                                lowestScore = val;
                                            }
                                            totalScore += val;
                                            totalScanned++;

                                            highScore.setText(highestScore.toString());
                                            lowScore.setText(lowestScore.toString());
                                            totalQRamount.setText(totalScanned.toString());
                                            totalQRScore.setText(totalScore.toString());
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
        }


    }
}
