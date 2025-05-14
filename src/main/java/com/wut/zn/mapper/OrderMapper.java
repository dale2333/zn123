package com.wut.zn.mapper;

import com.wut.zn.entity.Order;
import com.wut.zn.entity.dto.OrderDTO;
import com.github.pagehelper.Page;
import com.wut.zn.entity.dto.OrderVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {



    @Select("<script>" +
            "SELECT o.id, c.name, c.image, o.days, o.create_time as createTime, o.update_time as updateTime, " +
            "cc.category, k.kind, u.username, c.status " +
            "FROM `order` o " +
            "LEFT JOIN car c ON o.car_id = c.id " +
            "LEFT JOIN car_category cc ON c.car_categoryid = cc.id " +
            "LEFT JOIN kinds k ON c.kindid = k.id " +
            "LEFT JOIN user u ON c.userid = u.id " +
            "<where>" +
            "  <if test='orderNo != null'>AND o.order_no LIKE CONCAT('%',#{orderNo},'%')</if>" +
            "  <if test='carName != null'>AND c.name LIKE CONCAT('%',#{carName},'%')</if>" +
            "</where>" +
            "ORDER BY o.create_time DESC" +
            "</script>")
    List<OrderVO> selectOrderList(@Param("orderNo") String orderNo,
                                  @Param("carName") String carName);

    @Select("SELECT COUNT(*) FROM `order`")
    int countAllOrders();

    // 单个删除
    @Delete("DELETE FROM `order` WHERE id = #{id}")
    int deleteById(Long id);

    // 批量删除
    @Delete("<script>" +
            "DELETE FROM `order` WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchDelete(@Param("ids") List<Long> ids);

    @Insert("INSERT INTO `order` (car_id, user_id, days, car_status, create_time, update_time) " +
            "VALUES (#{carId}, #{userId}, #{days}, #{carStatus}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);


    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order selectById(Long id);

    // 更新订单
    @Update("UPDATE `order` SET " +
            "car_status = #{carStatus}, " +
            "update_time = #{updateTime} " +  // 使用新字段名
            "WHERE id = #{id}")
    int updateById(Order order);


    int update(Order order);
}

