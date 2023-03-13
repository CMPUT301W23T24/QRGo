package com.example.qrgo;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> users = new ArrayList<>();

    public List getUsers(){
        List list = users;
        return list;
    }
}
