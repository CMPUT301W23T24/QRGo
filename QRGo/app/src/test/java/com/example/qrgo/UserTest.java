package com.example.qrgo;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
public class UserTest {

    public MockDBUser MockUser() {
        // Set up the mock user data
        MockDBUser testUser = new MockDBUser("testDeviceID");

        return testUser;
    }


    @Test
    void getDeviceID() {
        MockDBUser testUser = MockUser();
        String id = testUser.getDeviceID();
        assertEquals(id, "testDeviceID");
    }
    @Test
    void getUserName() {
        MockDBUser testUser = MockUser();
        assertEquals("", testUser.getUserName());
    }
    @Test
    void setUserName() {
        MockDBUser testUser = MockUser();
        testUser.setUserName("test2");
        assertEquals("test2", testUser.getUserName());
    }
    @Test
    void getName() {
        MockDBUser testUser = MockUser();
        String name = testUser.getName();
        assertEquals("", name);

    }
    @Test
    void SetName() {
        MockDBUser testUser = MockUser();
        testUser.setName("name2");
        assertEquals("name2", testUser.getName());
    }
    @Test
    void getEmail() {
        MockDBUser testUser = MockUser();
        String email = testUser.getEmail();
        assertEquals("", email);
    }
    @Test
    void setEmail() {
        MockDBUser testUser = MockUser();
        testUser.setEmail("test2@email.com");
        assertEquals("test2@email.com", testUser.getEmail());
    }

    @Test
    void getPhoneNum() {
        MockDBUser testUser = MockUser();
        int phoneNum = testUser.getPhoneNum();
        assertEquals(0, phoneNum);
    }
    @Test
    void setPhoneNum() {
        MockDBUser testUser = MockUser();
        testUser.setPhoneNum(111);
        assertEquals(111, (int) testUser.getPhoneNum());
    }
    @Test
    void getScannedQRs() {
        MockDBUser testUser = MockUser();
        ArrayList<String> qrs = (ArrayList<String>) testUser.getScannedQRs();
        ArrayList<String> testqrs = new ArrayList<String>();
        assertEquals(qrs, testqrs);
    }
    @Test
    void addScannedQRs() {
        MockDBUser testUser = MockUser();
        String addedqr = "TestQR";
        testUser.addScannedQRs(addedqr);
        ArrayList<String> qrs = (ArrayList<String>) testUser.getScannedQRs();
        assertEquals(qrs.size(), 1);
    }
    @Test
    void getTotalScore() {
        MockDBUser testUser = MockUser();
        int score = testUser.getTotalScore();
        assertEquals(score, 0);
    }
    @Test
    void setTotalScore() {
        MockDBUser testUser = MockUser();
        testUser.setTotalScore(5);
        int score = testUser.getTotalScore();
        assertEquals(score, 5);
    }
    @Test
    void updateTotalScore() {
        MockDBUser testUser = MockUser();
        testUser.updateTotalScore(1);
        int score = testUser.getTotalScore();
        assertEquals(score, 1);
    }

}
