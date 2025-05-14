package com.wut.zn.service.impl;

import com.wut.zn.entity.User;
import com.wut.zn.mapper.UserMapper;
import com.wut.zn.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wut.zn.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User login(User user) {
        return userMapper.getUser(user);
    }

    @Override
    public PageInfo<User> getPagedUsers(Integer pageNo, Integer pageSize, String username) {
        // 启用分页（会自动拦截下一个查询）
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.selectByPage(username);
        return new PageInfo<>(users);
    }

    // 单个删除（带校验）
    public int deleteUser(Long id) {
        int affectedRows = userMapper.deleteById(id);
        if (affectedRows == 0) {
            throw new RuntimeException("用户ID不存在: " + id);
        }
        return affectedRows;
    }

    // 批量删除
    @Transactional
    public int batchDeleteUsers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ID列表不能为空");
        }
        return userMapper.batchDeleteByIds(ids);
    }


    @Transactional
    public void addUser(User user) {
        if (userMapper.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        userMapper.insertUser(user);
    }

    // 更新用户（带存在性校验）
    @Override
    public void updateUser(User user) {
        log.info("尝试更新用户ID: {}", user.getId());
        if (!userMapper.existsById(user.getId())) {
            log.error("用户不存在, ID: {}", user.getId());
            throw new RuntimeException("用户不存在");
        }
        userMapper.updateUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }


    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        userMapper.updateUser(user);
        return true;
    }

}
