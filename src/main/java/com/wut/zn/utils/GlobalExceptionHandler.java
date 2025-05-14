package com.wut.zn.utils;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleValidationError(MethodArgumentNotValidException ex) {
        return Result.fail("参数错误: " + ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> handleGenericError(Exception ex) {
        return Result.fail("服务器错误: " + ex.getMessage());
    }



}
