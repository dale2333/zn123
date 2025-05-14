package com.wut.zn.mapper;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.CarCategory;
import com.wut.zn.entity.Kinds;
import com.wut.zn.entity.dto.CarDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarMapper {
    // 三表关联分页查询
    List<CarDTO> selectWithRelations(
            @Param("name") String name,
            @Param("category") String category,
            @Param("kind") String kind);

    int deleteById(@Param("id") Long id);
    int batchDelete(@Param("ids") List<Long> ids);
    int insert(Car car);
    int update(Car car);

    // 可选：获取分类和类型下拉选项
    List<CarCategory> getAllCategories();
    List<Kinds> getAllKinds();


    CarDTO selectWithRelationsById(Long id);


    @Update({
            "UPDATE car SET",
            "name = #{name},",
            "image = #{image},",
            "car_categoryid = #{carCategoryId},",
            "kindid = #{kindId},",
            "update_time = #{updateTime}",
            "WHERE id = #{id}"
    })
    int updateById(Car car);

    @Select("SELECT * FROM car WHERE id = #{id}")
    Car selectById(Long id);
}
