package com.wut.zn.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private Long carId;
    private String name;
    private String category;
    private String kinds;
    private String image;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
