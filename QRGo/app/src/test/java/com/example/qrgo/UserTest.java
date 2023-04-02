package com.example.qrgo;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;

import android.os.Build;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@RunWith(RobolectricTestRunner.class)
public class UserTest {
    FirebaseFirestore db;
    private HashMap<String, Object> testUserData;
    private User testUser;

    @Before
    public void setUp() {
        // Set up the mock user data
        testUser = new User("testDeviceID");
        testUser.setUserName("test-username");
        testUser.setName("name");
        testUser.setEmail("test-email");
        testUser.setPhoneNum(123456789);
        List<String> scannedQRs = new ArrayList<>();
        testUser.setTotalScore(100);

        testUserData = new HashMap<>();
        testUserData.put("name", testUser.getName());
        testUserData.put("username", testUser.getUserName());
        testUserData.put("email", testUser.getEmail());
        testUserData.put("phoneNum", testUser.getPhoneNum());
        testUserData.put("scannedQRs", testUser.getScannedQRs());
        testUserData.put("totalScore", testUser.getTotalScore());
        db = FirebaseFirestore.getInstance();
    }

//    @Test
//    public void getUserName() {
//        assertEquals("test-username", testUser.getUserName());
//    }
//    @Test
//    void setUserName() {
//        testUser.setUserName("test2");
//        assertEquals("test2", testUser.getUserName());
//    }
//    @Test
//    void getName() {
//        String name = testUser.getName();
//        assertEquals("name", name);
//
//    }
//    @Test
//    void SetName() {
//        testUser.setName("name2");
//        assertEquals("name2", testUser.getName());
//    }
//    @Test
//    void getEmail() {
//        String email = testUser.getEmail();
//        assertEquals("test-email", email);
//    }
//    @Test
//    void setEmail() {
//        testUser.setEmail("test2@email.com");
//        assertEquals("test2@email.com", testUser.getEmail());
//    }
//
//    @Test
//    void getPhoneNum() {
//        int phoneNum = testUser.getPhoneNum();
//        assertEquals(123456789, phoneNum);
//    }
//    @Test
//    void setPhoneNum() {
//        testUser.setPhoneNum(111);
//        assertEquals(111, (int) testUser.getPhoneNum());
//    }
    @Test
    public void saveUserTest() {

//        // Mock the saveUser() method
//        doNothing().when(testUser).saveUser();
        // Call the saveUser() method
        testUser.saveUser();

        // Retrieve the saved data from the database
        DocumentReference savedDocRef = db.collection("user").document(testUser.getDeviceID());
        savedDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Check if the saved data matches the expected data
                assertEquals(testUserData.get("name"), documentSnapshot.get("name"));
                assertEquals(testUserData.get("username"), documentSnapshot.get("username"));
                assertEquals(testUserData.get("email"), documentSnapshot.get("email"));
                assertEquals(testUserData.get("phoneNum"), documentSnapshot.get("phoneNum"));
                assertEquals(testUserData.get("scannedQRs"), documentSnapshot.get("scannedQRs"));
                assertEquals(testUserData.get("totalScore"), documentSnapshot.get("totalScore"));
            }
        });
    }
}
