package com.wut.zn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Kinds {
    private Long id;
    private String kind;
    private Date createTime;
    private Date updateTime;
}
