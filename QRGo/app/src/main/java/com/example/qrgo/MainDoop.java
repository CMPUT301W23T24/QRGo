package com.example.qrgo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainDoop extends AppCompatActivity implements AddCommentFragment.AddCommentDialogListener {

    private ArrayList<Comment> dataList;
    private ListView commentList;
    private CommentArrayAdapter commentAdapter;

    private String hash = "8227ad036b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32";

    final String TAG = "Sample";
    FirebaseFirestore db;


    public void addComment(Comment comment) {
        commentAdapter.add(comment);
        db = FirebaseFirestore.getInstance();

        final String user_name = comment.getUserName();
        final String comment_text = comment.getComment();
        Map<String, String> data = new HashMap<>();

        if (user_name.length() > 0 && comment_text.length() > 0) {
            data.put(user_name, comment_text);


            CollectionReference collectionReference = db.collection("qr");
            collectionReference.document(hash)
                    .update("comments", FieldValue.arrayUnion(data));
            commentAdapter.notifyDataSetChanged();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comments);

        // example comments
        String[] user_names = {"The Rizzler", "The Rizzed"};
        String[] comments = {"I got Mother Mary on my side", "Holy Mother of God"};

        dataList = new ArrayList<Comment>();

        for (int i = 0; i < user_names.length; i++) {
            dataList.add(new Comment(user_names[i], comments[i]));
        }
         //dataList.addAll(Arrays.asList(user_names));

        commentList = findViewById(R.id.comment_list);
        commentAdapter = new CommentArrayAdapter(this,dataList);
        commentList.setAdapter(commentAdapter);


        Button add_button = findViewById(R.id.add_button_comment);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCommentFragment().show(getSupportFragmentManager(), "Add Comment");
            }
        });

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("qr");
        collectionReference.document(hash)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Log.d(TAG, "DATA TYPE " + document.get("comments").getClass());
                                ArrayList<HashMap<String, String>> comm = (ArrayList<HashMap<String, String>>) document.get("comments");
                                //Log.d(TAG, comm.get(0).);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });




    }

}
