package com.rrahul.uacf.rest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rrahul.uacf.rest.api.User;

public class UserDB {
     
    public static HashMap<String, User> userMap = new HashMap<String, User>();
    public static int count = 4;
    static{
        userMap.put("Rahul",new User("Rahul"));
        userMap.put("Under Armor",new User("Under Armor"));
        userMap.put("Connected Fitness", new User( "Connected Fitness"));
    }

    public static List<User> getAllUsers(){
        return new ArrayList<>(userMap.values());
    }
     
    public static User getUser(String userName){
        return userMap.get(userName);
    }

    public static int postMsg(String userName, String msg, Integer timeout) {
        User user = userMap.get(userName);
        System.out.println("timeout is " + timeout);
        if (timeout == null) {
            timeout = 60;
        }
        int id = 0;
        if (user != null) {
            id = user.setMsg(msg, timeout);
        } else {
            User newUser = new User(userName);
            id = newUser.setMsg(msg, timeout);
            userMap.put(userName, newUser);
        }
        return id;
    }
}