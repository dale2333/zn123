package com.wut.zn.mapper;

import com.wut.zn.entity.dto.ReturnCarDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReturnMapper {

    List<ReturnCarDTO> selectReturnCarList(@Param("returnNo") Long returnNo,
                                           @Param("carName") String carName);


    @Delete("DELETE FROM `return` WHERE id = #{id}")
    int deleteById(Long id);

    // 批量删除
    @Delete("<script>" +
            "DELETE FROM `return` WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchDelete(@Param("ids") List<Long> ids);
}
