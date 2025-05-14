package com.wut.zn.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class Car {
    private Long id;
    private Long userid;
    private String name;
    private String image;
    private Date createTime;
    private Date updateTime;
    private Long kindId;
    private Long carCategoryId;
    private Long money;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    @ApiModelProperty(value = "出租状态(0-未出租,1-已出租)", example = "0")
    private Integer status;

    // 状态常量定义
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_RENTED = 1;
}