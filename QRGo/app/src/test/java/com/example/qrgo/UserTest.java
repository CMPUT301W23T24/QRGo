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

//    @Test
//    void getDeviceID() {
//        NoDBUser testUser = new NoDBUser("123");
//        assertEquals("123", testUser.getDeviceID());
//    }

    @Test
    void getUserName() {
        NoDBUser testUser = MockUser();
        assertEquals("user_test_name", testUser.getUserName());
        }
//        assertEquals("test_name", testUser.getDeviceID());
//    }





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

}
