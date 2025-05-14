package com.wut.zn.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CarAddDTO {
    @NotBlank(message = "车辆名称不能为空")
    @Size(max = 50, message = "车辆名称最长50个字符")
    private String name;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotBlank(message = "车辆类型不能为空")
    private String kinds;

    @NotBlank(message = "图片不能为空")
    private String image;
}