package com.example.qrgo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Finds Friends within a search method
 */

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * Find Friends
 */
public class FindFriends extends AppCompatActivity {
        private Button back;
        private Button search;
        private EditText username;
        private ArrayList<User> users;
        private ArrayAdapter<User> userAdapter;
        private FirebaseFirestore db;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find_friends);

            ListView userList = (ListView) findViewById(R.id.qrSearchList);
            back= findViewById(R.id.backBtn);
            search = findViewById(R.id.searchFriend);
            username = findViewById(R.id.searchFriendText);

            db = FirebaseFirestore.getInstance();
            CollectionReference cr = db.collection("user");

        /**
         * Searches for the users  in DB
         */
        search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    users= new ArrayList<User>();
                    userAdapter= new UserListAdapter(FindFriends.this, users);
                    userList.setAdapter(userAdapter);

                    final String name = username.getText().toString();
                    if(name.length() > 0){
                        cr.whereGreaterThanOrEqualTo("username", name)
                                .whereLessThanOrEqualTo("username", name + '\uf8ff')
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (DocumentSnapshot snapshot: task.getResult()){
                                                User user= new User(snapshot.getId());
                                                user.getValuesFromDb(snapshot.getId(), new User.OnUserLoadedListener() {
                                                    @Override
                                                    public void onUserLoaded(User user) {
                                                    }
                                                });
                                                userAdapter.add(user);
                                                userAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });
                    }
                }
            });
            back.setOnClickListener(new View.OnClickListener() {

                /**
                 * Sends the user back
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
}
