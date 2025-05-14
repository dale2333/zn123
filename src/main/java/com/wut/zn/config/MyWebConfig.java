package com.wut.zn.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan("com.wut.zn.mapper")
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor handlerInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/layui/**")
                .addResourceLocations("classpath:/static/layui/");
    
        registry.addResourceHandler("/lib/**")
                .addResourceLocations("classpath:/static/lib/");
    
        // 关键：同时映射本地磁盘和classpath
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/upload/", "classpath:/static/images/");
    
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(handlerInterceptor);

        // 精确拦截这两个路径
        registration.addPathPatterns(
                "/admin_index.html",       // 拦截直接访问HTML文件
                "/user_index.html"
        );

        // 排除不需要拦截的路径
        registration.excludePathPatterns(
                "/login",            // 登录页面
                "/user/login",       // 登录接口
                "/user/logout",     // 登出接口
                "/static/**",       // 静态资源
                "/layui/**",
                "/webjars/**",
                "/images/**",
                "/favicon.ico",
                "/error"            // 错误页面
        );
    }
}