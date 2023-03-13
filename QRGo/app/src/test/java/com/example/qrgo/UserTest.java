package com.example.qrgo;

import static android.content.ContentValues.TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;


public class UserTest {
    private User MockUser() {
        User mockUser = new User("123");
        mockUser.setName("test_name");
        mockUser.setUserName("user_test_name");
        mockUser.setEmail("123@test.com");
        mockUser.setPhoneNum(123);
        return mockUser;
    }

    @Test
    void getDeviceID() {
        User testUser = MockUser();
        assertEquals("123", testUser.getDeviceID());

    }
    @Test
    void getUserName() {
        User testUser = MockUser();
        String name = testUser.getName();
        assertEquals("test_name", name);
    }

    /*
    @Test
    public void testUserSave() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference= db.collection("user");

        String user_id = "test_device_id";
        // Initialize user
        User user = new User(user_id);
        user.setName("Test User");
        user.setUserName("test_user");
        user.setEmail("test@example.com");
        user.setPhoneNum(123456789);

        // Save user
        user.saveUser();

        // Retrieve user from database
        collectionReference.document("user1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        // Check if data is correct
                        // assertEquals("Test User", doc.getData().get(""));
                        assertEquals("u1", "u1");
                        //assertEquals("test@example.com", doc.get("email"));
                        //assertEquals("123456789", (doc.get("phoneNum")));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    @Test
    public void testValuesFromDb() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference= db.collection("user");
        DocumentReference docRef = collectionReference.document("user1");

        // Retrieve user from database
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        assertEquals(0, docRef.get('phoneNum'));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    */
}
