package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserListTest {
    @Test
    void getUsers() {
        UserList newlist = new UserList();
        List<User> users = new ArrayList<>();
        assertEquals(newlist.getUsers(), users);
    }
}
