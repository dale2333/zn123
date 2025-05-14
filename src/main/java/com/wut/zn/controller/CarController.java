package com.wut.zn.controller;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.CarCategory;
import com.wut.zn.entity.dto.CarUpdateDTO;
import com.wut.zn.entity.Kinds;
import com.wut.zn.entity.dto.CarDTO;
import com.wut.zn.service.CarService;
import com.wut.zn.utils.ResourceNotFoundException;
import com.wut.zn.utils.Result;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/paged")
    public Result<PageInfo<CarDTO>> paged(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String kind) {

        PageInfo<CarDTO> pageInfo = carService.getPagedCars(
                pageNo, pageSize, name, category, kind);
        return Result.success(pageInfo);
    }

//    @PostMapping
//    public Result<?> saveCar(@RequestBody Car car) {
//        return carService.saveOrUpdateCar(car) > 0
//                ? Result.success()
//                : Result.fail("操作失败");
//    }
    @PostMapping
    public Result<?> saveCar(@RequestBody @Valid Car car) {
        // 确保money字段被处理
        if(car.getMoney() == null || car.getMoney() < 0) {
            return Result.fail("日租金必须大于等于0");
        }
        return carService.saveOrUpdateCar(car) > 0
                ? Result.success()
                : Result.fail("操作失败");
        }



    @DeleteMapping("/{id}")
    public Result<?> deleteCar(@PathVariable Long id) {
        return carService.deleteById(id) > 0
                ? Result.success()
                : Result.fail("删除失败");
    }

    @DeleteMapping("/batch")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        int count = carService.batchDelete(ids);
        return count > 0
                ? Result.success().data(count)
                : Result.fail("批量删除失败");
    }

    @GetMapping("/categories")
    public Result<List<CarCategory>> getCategories() {
        // 添加日志输出，确认查询结果
        List<CarCategory> categories = carService.getAllCategories();
        log.info("查询到的分类数据：{}", categories);
        return Result.success(categories);
    }

    @GetMapping("/kinds")
    public Result<List<Kinds>> getKinds() {
        // 强制不缓存
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");

        List<Kinds> kinds = carService.getAllKinds();
        log.info("准备返回的kinds数据: {}", kinds);
        return new Result<List<Kinds>>()
                .code(0)
                .message("success")
                .data(kinds);
    }

    @GetMapping("/{id}")
    public Result<CarDTO> getCarById(@PathVariable Long id) {
        try {
            CarDTO car = carService.getCarWithRelations(id);
            return Result.success(car);
        } catch (ResourceNotFoundException e) {
            log.error("获取车辆详情失败: {}", e.getMessage());
            // 使用 typedFail 方法保持类型一致
            return Result.<CarDTO>typedFail(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateCar(
            @PathVariable Long id,
            @RequestBody @Validated CarUpdateDTO carDTO) {

        try {
            // 确保ID一致性
            carDTO.setId(id);
            boolean success = carService.updateCar(carDTO);
            return success ? Result.success() : Result.fail("更新失败");
        } catch (ResourceNotFoundException e) {
            log.error("更新车辆失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("更新车辆发生异常: ", e);
            return Result.fail("更新车辆时发生错误");
        }
    }


}
