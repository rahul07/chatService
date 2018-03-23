package com.rrahul.uacf.rest.api.model;

import lombok.Data;

@Data
public class PostRequest {
    String userName;

    String msgText;

    Integer timeout;
}
