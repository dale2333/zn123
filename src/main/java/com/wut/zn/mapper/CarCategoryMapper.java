package com.wut.zn.mapper;

import com.wut.zn.entity.CarCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CarCategoryMapper {
    List<CarCategory> selectByPage(@Param("category") String category);
    int deleteById(Long id);
    int batchDeleteByIds(@Param("ids") List<Long> ids);
    int insert(CarCategory category);
    int update(CarCategory category);
    boolean existsByName(String category);
    boolean existsById(Long id);
    @Select("SELECT * FROM car_category WHERE id = #{id}")
    CarCategory selectById(Long id);


    List<CarCategory> selectAll();
}
