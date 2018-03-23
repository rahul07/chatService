package com.rrahul.uacf.rest.api;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class User {
    private final String userName;
    private List<UserDetails> userDetails;

    public User(String name) {
        this.userName = name;
        this.userDetails = new ArrayList<>();
    }

    public String getUsername() {
        return userName;
    }

    public int setMsg(String msg, int timeout) {
        int id = Math.abs(new Random().nextInt());
        UserDetails userDetails = new UserDetails();
        userDetails.setMsg(msg);
        userDetails.setMsgTimestamp(Instant.now().plusSeconds(timeout).getEpochSecond());
        userDetails.setId(id);
        userDetails.setExpired(false);
        List<UserDetails> userDetailsList = this.userDetails;
        userDetailsList.add(userDetails);
        this.userDetails = userDetailsList;
        return id;
    }

}
