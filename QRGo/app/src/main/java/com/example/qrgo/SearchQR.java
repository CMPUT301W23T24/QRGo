package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchQR extends AppCompatActivity {

    ListView qrList;
    ArrayAdapter<QR> qrAdapter;
    EditText searchQRET;
    Button qrSearchB;
    FirebaseFirestore db;
    ArrayList<QR> qrs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_qr);

        searchQRET = findViewById(R.id.searchQRET);
        qrSearchB = findViewById(R.id.searchQRB);
        qrs = new ArrayList<QR>();
        qrAdapter = new QRListAdapter(this, qrs);
        db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("qr");

        qrSearchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = searchQRET.getText().toString();
                if (name.length() > 0){
                    cr.whereArrayContains("name", name).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        //TODO find a way to paste what is found into the LV
                                        Log.d(TAG, "found!!!");

                                    }
                                }
                            });

                }
            }
        });

    }


}
