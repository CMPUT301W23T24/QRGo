package com.example.qrgo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TotalScoreBoard extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ArrayList<User> users;
    private TotalArrayAdapter totalAdapter;
    private ListView userList;
    final String TAG = "Sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_scoreboard);

        userList = findViewById(R.id.TotalScoreBoard);
        users = new ArrayList<User>();
        totalAdapter = new TotalArrayAdapter(TotalScoreBoard.this, users);


    }
}
