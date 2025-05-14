package com.wut.zn.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReturnCarDTO {
    // 归还表字段
    private Long returnId;
    private Long userId;
    private Long carId;
    private Date returnCreateTime;
    private Date returnUpdateTime;

    // 车辆表字段
    private String carName;
    private String carImage;
    private Integer carStatus;
    private Date carCreateTime;
    private Date carUpdateTime;

    // 用户表字段
    private String username;
    private String phone;

    // 车辆类别表字段
    private String category;

    // 车辆类型表字段
    private String kind;
}
