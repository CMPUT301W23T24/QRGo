package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;




public class UserTest {

    private NoDBUser MockUser() {
        NoDBUser ndbu = new NoDBUser("test_name");
        ndbu.setEmail("@ua");
        ndbu.setUserName("im sad");
        ndbu.setName("hi sad im dad");
        ndbu.setPhoneNum(123556313);
        return ndbu;
    }

    @Test
    void getUserName() {
        NoDBUser testUser = MockUser();
        assertEquals("test_name", testUser.getDeviceID());
    }





}
