package com.wut.zn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CarCategory {
    private Long id;
    private String category;
    private Date createTime;
    private Date updateTime;

}
