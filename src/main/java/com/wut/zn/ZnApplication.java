package com.wut.zn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wut.zn.mapper" )
public class ZnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZnApplication.class, args);
    }

}
