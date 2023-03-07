package com.example.qrgo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class QRDetails extends AppCompatActivity {
    private TextView QRFaceTV;
    private TextView nameTV;
    private TextView scoreTV;
    private Button locationB;
    private Button photoB;
    private Button scannersB;
    private Button commentsB;
    private Button deleteB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_details);
        String content = "BFG5DGW54";
        String hash;
        Integer score;
        String face;
        String name;
        QRFaceTV = (TextView) findViewById(R.id.TVQRFace);
        nameTV = (TextView) findViewById(R.id.TVName);
        scoreTV = (TextView) findViewById(R.id.TVScore);

        locationB = (Button) findViewById(R.id.LocationButton);
        photoB = (Button) findViewById(R.id.PhotoButton);
        scannersB = (Button) findViewById(R.id.ScannersButton);
        commentsB = (Button) findViewById(R.id.CommentsButton);
        deleteB = (Button) findViewById(R.id.DeleteButton);

        QRReader qrContent = new QRReader();
        hash = qrContent.createHash(content);
        score = qrContent.calcScore(hash);
        face = qrContent.createFace(hash);
        name = qrContent.createName(hash);

        QR qr = new QR(name, "user", score, face);

        QRFaceTV.setText(face);
        nameTV.setText(name);
        scoreTV.setText(score.toString());



        locationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Not sure if anyone else set up a location class so I left it out
            }
        });

        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO As before a class might exist for this (maybe Faiyad)?
                //TODO Also might be easier to make a fragment over instead of creating a whole new activity
            }
        });

        scannersB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO activity? Fragment?
                //Listview fragment probably
            }
        });

        commentsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO activity? Fragment?
            }
        });

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO remove the QR from DB
            }
        });

    }
}
