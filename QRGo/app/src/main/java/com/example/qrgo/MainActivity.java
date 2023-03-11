package com.example.qrgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    // This is a test
    FirebaseFirestore db;

    Button comment;
    Button scanned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_pg);

        comment = findViewById(R.id.comment);
        scanned = findViewById(R.id.people_scanned);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainDoop.class);
                startActivity(intent);
            }
        });

        scanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScannedDoop.class);
                startActivity(intent);
            }
        });

    }


}