package com.wut.zn.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Integer count; // 改为 Integer 类型



    // 链式方法：设置 data
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    // 链式方法：设置 count
    public Result<T> count(Integer count) {
        this.count = count;
        return this;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    // 静态方法（保持不变）
    public static Result<Object> success() {
        return new Result<>(0, "success", null, null);
    }

    public static Result<Object> fail() {
        return new Result<>(-1, "fail", null, null);
    }

    public static Result<Object> fail(String message) {
        return new Result<>(-1, message, null, null);
    }

    // 分页专用成功方法（新增）
    public static <T> Result<List<T>> pageSuccess(List<T> data, Integer total) {
        return new Result<List<T>>(0, "success", data, total);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data, null);
    }

    public static <T> Result<T> typedSuccess(T data) {
        return new Result<>(0, "success", data, null);
    }

    public static <T> Result<T> typedFail(String message) {
        return new Result<>(-1, message, null, null);
    }







}