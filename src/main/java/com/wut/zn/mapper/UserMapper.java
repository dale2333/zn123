package com.wut.zn.mapper;

import com.wut.zn.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {
    User getUser(User user);

    List<User> selectByPage(@Param("username") String username);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);

    // 批量删除（动态 SQL）
    int batchDeleteByIds(@Param("ids") List<Long> ids);


    @Insert("INSERT INTO user (username, password, sex, phone) VALUES (#{username}, #{password}, #{sex}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    int updateUser(User user);

    // 新增方法: 根据ID查询用户是否存在
    @Select("SELECT COUNT(*) FROM user WHERE id = #{id}")
    boolean existsById(Long id);

    // 新增方法: 根据用户名查询用户是否存在
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    boolean existsByUsername(String username);

    // 新增方法: 根据ID查询用户详情（用于存在性校验）

    User selectById(Long id);


}
