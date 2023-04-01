package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;


public class UserTest {

    @Mock
    private FirebaseFirestore db;

    @Mock
    private CollectionReference collectionReference;

    private User MockUser() {
        MockitoAnnotations.initMocks(this);
        User user = new User("deviceID");
        user.setName("testName");
        user.setUserName("testUserName");
        user.setEmail("testEmail");
        user.setPhoneNum(1234567890);
        user.addScannedQRs("TestQR1");
        return user;
    }

//    @Test
//    void getDeviceID() {
//        NoDBUser testUser = new NoDBUser("123");
//        assertEquals("123", testUser.getDeviceID());
//    }

    @Test
    public void getUserName() {
        User testUser = MockUser();
        assertEquals("user_test_name", testUser.getUserName());
        }
//        assertEquals("test_name", testUser.getDeviceID());
//    }





    @Test
    public void setUserName() {
        User testUser = MockUser();
        testUser.setUserName("test2");
        assertEquals("test2", testUser.getUserName());
    }
    @Test
    public void getName() {
        User testUser = MockUser();
        String name = testUser.getName();
        assertEquals("test_name", name);
    }
    @Test
    public void SetName() {
        User testUser = MockUser();
        testUser.setName("name2");
        assertEquals("name2", testUser.getName());
    }
    @Test
    public void getEmail() {
        User testUser = MockUser();
        String email = testUser.getEmail();
        assertEquals("123@test.com", email);
    }
    @Test
    public void setEmail() {
        User testUser = MockUser();
        testUser.setEmail("test2@email.com");
        assertEquals("test2@email.com", testUser.getEmail());
    }

    @Test
    public void getPhoneNum() {
        User testUser = MockUser();
        int phoneNum = testUser.getPhoneNum();
        assertEquals(123, phoneNum);
    }
    @Test
    public void setPhoneNum() {
        User testUser = MockUser();
        testUser.setPhoneNum(111);
        assertEquals(111, (int) testUser.getPhoneNum());
    }
    @Test
    public void saveUser() {
        User testUser = MockUser();
        // Mock the behavior of the connectToDB() method
        when(db.getInstance()).thenReturn(collectionReference);
        when(collectionReference.document(testUser.getDeviceID())).thenReturn(collectionReference);

        // Call the saveUser() method
        testUser.saveUser();

        // Verify that the set() method is called with the correct parameters
        HashMap<String, Object> expectedData = new HashMap<>();
        expectedData.put("name", "testName");
        expectedData.put("username", "testUserName");
        expectedData.put("email", "testEmail");
        expectedData.put("phoneNum", 1234567890);
        expectedData.put("scannedQRs", testUser.getScannedQRs());
        verify(collectionReference).set(expectedData);

    }

}
