package com.rrahul.uacf.rest.api.model;

import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class GetIdResponse {
    private String userName;

    private String text;

    private String expiration_date;
}