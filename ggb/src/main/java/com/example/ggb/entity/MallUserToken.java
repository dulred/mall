package com.example.ggb.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MallUserToken {
    private Long userId;
    private String token;
    private Date updateTime ;
    private Date expireTime;
}
