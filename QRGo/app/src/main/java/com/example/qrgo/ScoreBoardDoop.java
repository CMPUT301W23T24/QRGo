package com.example.qrgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreBoardDoop extends AppCompatActivity {

    Button totals;
    Button highest_qr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_scoreboards);


        totals = findViewById(R.id.total_button);
        highest_qr = findViewById(R.id.highest_qr_button);

        /**
         * open up the Total Score-Based leaderboards/scoreboards
         * @param view
         */
        totals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreBoardDoop.this, TotalScoreBoard.class);
                startActivity(intent);
            }
        });

        /**
         * open up the Highest QR-Based leaderboards/scoreboards
         * @param view
         */
        highest_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreBoardDoop.this, QRScoreBoard.class);
                startActivity(intent);
            }
        });
    }
}
