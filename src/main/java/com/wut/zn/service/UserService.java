package com.wut.zn.service;

import com.wut.zn.entity.User;
import com.github.pagehelper.PageInfo;
import com.wut.zn.utils.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {


    User login(User param);


    PageInfo<User> getPagedUsers(Integer pageNo, Integer pageSize, String username);

    int deleteUser(Long id);
    int batchDeleteUsers(List<Long> ids);
    void addUser(User user);
    void updateUser(User user);
    User getUserById(Long id);

    boolean changePassword(Long userId, String oldPassword, String newPassword);

}
