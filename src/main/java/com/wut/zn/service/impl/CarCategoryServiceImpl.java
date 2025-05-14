package com.wut.zn.service.impl;

import com.wut.zn.entity.CarCategory;
import com.wut.zn.mapper.CarCategoryMapper;
import com.wut.zn.service.CarCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CarCategoryServiceImpl implements CarCategoryService {
    @Autowired
    private CarCategoryMapper categoryMapper;

    @Override
    public PageInfo<CarCategory> getPagedCategories(Integer pageNo, Integer pageSize, String category) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo<>(categoryMapper.selectByPage(category));
    }

    @Transactional
    @Override
    public void addCategory(CarCategory category) {
        if(categoryMapper.existsByName(category.getCategory())){
            throw new RuntimeException("分类名称已存在");
        }
        categoryMapper.insert(category);
    }



    @Transactional
    @Override
    public void deleteCategory(Long id) {
        if(categoryMapper.deleteById(id) == 0){
            throw new RuntimeException("分类不存在");
        }
    }

    @Transactional
    @Override
    public void batchDelete(List<Long> ids) {
        if(ids == null || ids.isEmpty()){
            throw new IllegalArgumentException("ID列表不能为空");
        }
        categoryMapper.batchDeleteByIds(ids);
    }

    @Override
    public CarCategory getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Transactional
    @Override
    public void updateCategory(CarCategory category) {
        // 存在性校验
        if (!categoryMapper.existsById(category.getId())) {
            throw new RuntimeException("分类不存在");
        }

        // 名称重复校验
        CarCategory oldCategory = categoryMapper.selectById(category.getId());
        if (!oldCategory.getCategory().equals(category.getCategory())
                && categoryMapper.existsByName(category.getCategory())) {
            throw new RuntimeException("分类名称已存在");
        }

        categoryMapper.update(category);
    }
}
