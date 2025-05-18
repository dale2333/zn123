package com.wut.zn.controller;

import com.wut.zn.entity.User;
import com.wut.zn.service.UserService;
import com.wut.zn.utils.Result;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // 构造器注入（推荐）

    @PostMapping("/login")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result<User> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpServletRequest request,
            HttpSession session
    ) {
        // 1. 校验验证码
        if (!CaptchaUtil.ver(captcha, request)) {
            return (Result<User>) (Result<?>) Result.fail("验证码错误！");
        }

        // 2. 构建查询参数
        User param = new User();
        param.setUsername(username);
        param.setPassword(password);

        // 3. 执行登录
        User user = userService.login(param);
        if (user == null) {
            return (Result<User>) (Result<?>) Result.fail("用户名或密码错误！");
        }

        // 4. 存储完整用户信息到 Session
        session.setAttribute("currentUser", user);

        return (Result<User>) (Result<?>) Result.success().data(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request) {
        // 处理注销逻辑
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 销毁会话
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/paged")
    public Result<Object> getPagedUsers(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username
    ) {
        PageInfo<User> pageInfo = userService.getPagedUsers(pageNo, pageSize, username);
        // 使用 Result 的链式调用设置分页数据
        return Result.success()
                .data(pageInfo.getList())
                .count((int) pageInfo.getTotal());
    }

    // 单个删除
    @DeleteMapping("/{id}")
    public Result<Object> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return Result.success().message("用户删除成功");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    // 批量删除
    @DeleteMapping("/batch")
    public Result<Object> batchDelete(@RequestBody List<Long> ids) {
        try {
            userService.batchDeleteUsers(ids);
            return Result.success().message("成功删除 " + ids.size() + " 条记录");
        } catch (IllegalArgumentException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result<?> addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return Result.success();
    }

    @PutMapping("/my-profile")
    public Result<?> updateMyProfile(@RequestBody Map<String, Object> params, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return Result.fail("未登录");
        }
        // 只允许修改部分字段
        if(params.get("sex") != null) currentUser.setSex((String)params.get("sex"));
        if(params.get("phone") != null) currentUser.setPhone((String)params.get("phone"));
        // if(params.get("address") != null) currentUser.setAddress((String)params.get("address"));
        userService.updateUser(currentUser);
        session.setAttribute("currentUser", currentUser);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        // 1. 处理用户不存在的情况
        if (user == null) {
            Result<User> errorResult = new Result<>();
            errorResult.setCode(-1);
            errorResult.setMessage("用户不存在");
            errorResult.setData(null); // 明确设置 data 为 null
            return errorResult;
        }
        else{
            // 2. 成功返回用户数据
            Result<User> successResult = new Result<>();
            successResult.setCode(0);
            successResult.setMessage("success");
            successResult.setData(user);
            return successResult;
        }
    }

    @GetMapping("/me")
    public Result<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            Result<User> errorResult = new Result<>();
            errorResult.setCode(-1);
            errorResult.setMessage("未登录");
            errorResult.setData(null);
            return errorResult;
        }
        Result<User> successResult = new Result<>();
        successResult.setCode(0);
        successResult.setMessage("success");
        successResult.setData(user);
        return successResult;
    }

    @PostMapping("/change-password")
    public Result<?> changePassword(@RequestBody Map<String, String> params, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return Result.fail("未登录");
        }
        String newPassword = params.get("newPassword");
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return Result.fail("新密码不能为空");
        }
        // 密码长度校验
        if (newPassword.length() < 6 || newPassword.length() > 20) {
            return Result.fail("密码必须6到20位");
        }
        currentUser.setPassword(newPassword);
        userService.updateUser(currentUser);
        session.setAttribute("currentUser", currentUser);
        return Result.success().message("密码修改成功");
    }

    // 添加用户更新接口
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody Map<String, Object> params) {
        try {
            // 获取用户ID
            Long userId = Long.parseLong(params.get("id").toString());
            
            // 获取当前用户信息
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                return Result.fail("用户不存在");
            }
            
            // 设置要更新的字段
            if (params.get("sex") != null) {
                existingUser.setSex((String) params.get("sex"));
            }
            
            if (params.get("phone") != null) {
                existingUser.setPhone((String) params.get("phone"));
            }
            
            // 处理密码：只有当密码不为空时才更新密码
            String password = (String) params.get("password");
            if (password != null && !password.trim().isEmpty()) {
                // 密码长度校验
                if (password.length() < 6 || password.length() > 20) {
                    return Result.fail("密码必须6到20位且不能包含空格");
                }
                existingUser.setPassword(password);
            }
            
            // 更新用户信息
            userService.updateUser(existingUser);
            return Result.success().message("用户更新成功");
        } catch (Exception e) {
            return Result.fail("更新用户失败: " + e.getMessage());
        }
    }
}

