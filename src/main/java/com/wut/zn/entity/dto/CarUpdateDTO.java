package com.wut.zn.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CarUpdateDTO {
    private Long id;
    @NotBlank(message = "车辆名称不能为空")
    private String name;
    private String image;
    @NotNull(message = "车辆品牌不能为空")
    private Long carCategoryId;
    @NotNull(message = "车辆类型不能为空")
    private Long kindId;
}
