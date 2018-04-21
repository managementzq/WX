package com.nbkj.wchatdemo.model;

import lombok.Data;

@Data
public class AccessToken {
    private String token;
    private int expiresIn;
}
