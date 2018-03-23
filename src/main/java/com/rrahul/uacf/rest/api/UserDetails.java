package com.rrahul.uacf.rest.api;

import lombok.Data;

@Data
public class UserDetails {
    private int id;
    private String msg;
    private Long msgTimestamp;
    private boolean expired;

}
