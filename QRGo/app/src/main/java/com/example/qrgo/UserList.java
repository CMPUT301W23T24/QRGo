package com.example.qrgo;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a list of users
 */
public class UserList {
    private List<User> users = new ArrayList<>();

    /**
     * Retrieves all lists from a user
     * @return list of users
     */
    public List getUsers(){
        List list = users;
        return list;
    }
}
