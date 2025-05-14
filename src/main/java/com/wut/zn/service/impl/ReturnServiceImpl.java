package com.wut.zn.service.impl;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.Return;
import com.wut.zn.entity.dto.ReturnCarDTO;
import com.wut.zn.mapper.CarCategoryMapper;
import com.wut.zn.mapper.CarMapper;
import com.wut.zn.mapper.ReturnMapper;
import com.wut.zn.mapper.UserMapper;
import com.wut.zn.service.ReturnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ReturnServiceImpl implements ReturnService {

    @Autowired
    private ReturnMapper returnMapper;



    @Override
    public List<ReturnCarDTO> getReturnCarList(Long returnNo, String carName) {
        return returnMapper.selectReturnCarList(returnNo, carName);
    }
    @Transactional
    public int deleteReturn(Long id) {
        return returnMapper.deleteById(id);
    }

    // 批量删除
    @Transactional
    public int batchDeleteReturn(List<Long> ids) {
        return returnMapper.batchDelete(ids);
    }

}
