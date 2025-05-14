package com.wut.zn.service.impl;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.CarCategory;
import com.wut.zn.entity.dto.CarUpdateDTO;
import com.wut.zn.entity.Kinds;
import com.wut.zn.entity.dto.CarDTO;
import com.wut.zn.mapper.CarMapper;
import com.wut.zn.service.CarService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wut.zn.utils.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public PageInfo<CarDTO> getPagedCars(Integer pageNo, Integer pageSize,
                                         String name, String category, String kind) {
        PageHelper.startPage(pageNo, pageSize);
        List<CarDTO> list = carMapper.selectWithRelations(name, category, kind);
        return new PageInfo<>(list);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return carMapper.deleteById(id);
    }

    @Transactional
    @Override
    public int batchDelete(List<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ID列表不能为空");
        }
        return carMapper.batchDelete(ids);
    }

    @Transactional
    @Override
    public int saveOrUpdateCar(Car car) {
        if(car.getId() == null) {
            car.setCreateTime(new Date());
            return carMapper.insert(car);
        } else {
            car.setUpdateTime(new Date());
            return carMapper.update(car);
        }
    }

    @Override
    public List<CarCategory> getAllCategories() {
        return carMapper.getAllCategories();
    }

    @Override
    public List<Kinds> getAllKinds() {
        return carMapper.getAllKinds();
    }

    @Override
    public CarDTO getCarWithRelations(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID不能为空");
        }
        CarDTO carDTO = carMapper.selectWithRelationsById(id);
        if (carDTO == null) {
            throw new ResourceNotFoundException("未找到ID为" + id + "的车辆");
        }
        return carDTO;
    }

    @Transactional
    @Override
    public boolean updateCar(CarUpdateDTO carDTO) {
        if (carDTO == null || carDTO.getId() == null) {
            throw new IllegalArgumentException("车辆ID不能为空");
        }

        // 检查车辆是否存在
        Car existingCar = carMapper.selectById(carDTO.getId());
        if (existingCar == null) {
            throw new ResourceNotFoundException("未找到ID为" + carDTO.getId() + "的车辆");
        }

        // 将DTO转换为实体
        Car car = new Car();
        BeanUtils.copyProperties(carDTO, car);
        car.setUpdateTime(new Date());

        // 执行更新
        return carMapper.updateById(car) > 0;
    }





    @Override
    public Car getCarById(Long id) {
        return carMapper.selectById(id);
    }

    @Override
    public void updateCar(Car car) {
        carMapper.updateById(car);
    }



}