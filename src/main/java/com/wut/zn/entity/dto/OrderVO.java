package com.wut.zn.entity.dto;


import lombok.Data;
import java.util.Date;

@Data
public class OrderVO {
    private Long id;          // 订单ID
    private String name;      // 车辆名称
    private String image;     // 车辆图片
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间
    private String category;  // 车辆分类
    private String kind;      // 车辆类型
    private String username;  // 用户名
    private Integer status;   // 车辆状态(0-未出租,1-已出租)
}
