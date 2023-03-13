package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class UserTest {
    private NoDBUser MockUser() {
        NoDBUser user = new NoDBUser("123");
        user.setName("test_name");
        user.setUserName("user_test_name");
        user.setEmail("123@test.com");
        user.setPhoneNum(123);
        return user;
    }

    @Test
    void getDeviceID() {
        NoDBUser testUser = new NoDBUser("123");
        assertEquals("123", testUser.getDeviceID());
    }

    @Test
    void getUserName() {
        NoDBUser testUser = MockUser();
        assertEquals("user_test_name", testUser.getUserName());
    }
    @Test
    void setUserName() {
        NoDBUser testUser = MockUser();
        testUser.setUserName("test2");
        assertEquals("test2", testUser.getUserName());
    }
    @Test
    void getName() {
        NoDBUser testUser = MockUser();
        String name = testUser.getName();
        assertEquals("test_name", name);
    }
    @Test
    void SetName() {
        NoDBUser testUser = MockUser();
        testUser.setName("name2");
        assertEquals("name2", testUser.getName());
    }
    @Test
    void getEmail() {
        NoDBUser testUser = MockUser();
        String email = testUser.getEmail();
        assertEquals("123@test.com", email);
    }
    @Test
    void setEmail() {
        NoDBUser testUser = MockUser();
        testUser.setEmail("test2@email.com");
        assertEquals("test2@email.com", testUser.getEmail());
    }

    @Test
    void getPhoneNum() {
        NoDBUser testUser = MockUser();
        int phoneNum = testUser.getPhoneNum();
        assertEquals(123, phoneNum);
    }
    @Test
    void setPhoneNum() {
        NoDBUser testUser = MockUser();
        testUser.setPhoneNum(111);
        assertEquals(111, (int) testUser.getPhoneNum());
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
