package com.wut.zn.service;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.CarCategory;
import com.wut.zn.entity.dto.CarUpdateDTO;
import com.wut.zn.entity.Kinds;
import com.wut.zn.entity.dto.CarDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CarService {
    PageInfo<CarDTO> getPagedCars(Integer pageNo, Integer pageSize,
                                  String name, String category, String kind);
    int deleteById(Long id);
    int batchDelete(List<Long> ids);
    int saveOrUpdateCar(Car car);

    // 获取下拉选项
    List<CarCategory> getAllCategories();
    List<Kinds> getAllKinds();
    CarDTO getCarWithRelations(Long id);
    boolean updateCar(CarUpdateDTO carDTO);

    Car getCarById(Long id);
    void updateCar(Car car);

}
