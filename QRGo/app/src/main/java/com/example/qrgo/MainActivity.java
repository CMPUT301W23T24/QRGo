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
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, QRDetails.class);
        startActivity(intent);

         scanQRButton = findViewById(R.id.scanQRButton);
         textView = findViewById(R.id.textView);
         scanQRButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 scanQRCode();
             }
         });
    }

    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
       if (result.getContents() != null) {
           AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           builder.setTitle("Result");
           builder.setMessage(result.getContents());
           textView.setText(result.getContents());
           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
               }
           }).show();
       }
    });
}