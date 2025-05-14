package com.wut.zn.service;

import com.wut.zn.entity.CarCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CarCategoryService {
    PageInfo<CarCategory> getPagedCategories(Integer pageNo, Integer pageSize, String categoryName);
    void addCategory(CarCategory category);
    void updateCategory(CarCategory category);
    void deleteCategory(Long id);
    void batchDelete(List<Long> ids);

    CarCategory getCategoryById(Long id);
}
