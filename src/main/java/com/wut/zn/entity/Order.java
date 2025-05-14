package com.wut.zn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Long id;
    private Long carId;
    private Long userId;
    private Long days;
    private Integer carStatus;
    private Date createTime;
    private Date updateTime;
}
