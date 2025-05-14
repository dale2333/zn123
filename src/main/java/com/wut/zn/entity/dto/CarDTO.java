package com.wut.zn.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class CarDTO {
    private Long id;             // 车辆ID
    private String name;         // 车辆名称
    private Long userid;
    private String category;    // 车辆分类（来自car_category表）
    private String kind;         // 车辆类型（来自kinds表）
    private String image;        // 图片路径
    private Date createTime;     // 创建时间
    private Date updateTime;     // 更新时间
    private Long money;
    private Long days;
    // 可选：添加关联ID字段（用于表单提交）
    private Long kindId;
    private Long carCategoryId;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    @ApiModelProperty(value = "出租状态(0-未出租,1-已出租)", example = "0")
    private Integer status;

    // 状态常量定义
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_RENTED = 1;
}
