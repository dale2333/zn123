package com.wut.zn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Return {
    private Long id;
    private Long userId;
    private Long carId;

    private Date createTime;
    private Date updateTime;
}
