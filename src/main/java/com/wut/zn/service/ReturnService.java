package com.wut.zn.service;

import com.wut.zn.entity.dto.ReturnCarDTO;

import java.util.List;

public interface ReturnService {
    List<ReturnCarDTO> getReturnCarList(Long returnNo, String carName);

    int batchDeleteReturn(List<Long> ids);
    int deleteReturn(Long id);
}
