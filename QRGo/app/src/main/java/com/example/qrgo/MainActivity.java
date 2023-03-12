package com.example.qrgo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import android.widget.TextView;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    Button scanQRButton;
    Button searchQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up scanQR button functionality
         scanQRButton = findViewById(R.id.scanQRButton);
         searchQR = findViewById(R.id.goToSearchB);
         scanQRButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 scanQRCode();
             }
         });

        searchQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchQR.class);
                startActivity(intent);

            }
        });
    }

    /**
     * sets up the options and launches the cameraActivity
     */
    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        // scan the code and pass its result to the qr details activity
       if (result.getContents() != null) {
           String qrContent = result.getContents();
           Intent intent = new Intent(this, QRDetails.class);
           intent.putExtra("qrContent", qrContent);
           startActivity(intent);
       }
    });
}