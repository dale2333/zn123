package com.wut.zn.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorController {

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/admin_index")
    public String index(){
        return "admin_index";
    }

    @GetMapping("/user_index")
    public String userIndex(){
        return "user_index";
    }



    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CaptchaUtil.out(request,response);
    }
}
