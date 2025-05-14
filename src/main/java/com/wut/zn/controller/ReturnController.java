package com.wut.zn.controller;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.Order;
import com.wut.zn.entity.dto.ReturnCarDTO;
import com.wut.zn.service.CarService;
import com.wut.zn.service.OrderService;
import com.wut.zn.service.ReturnService;
import com.wut.zn.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private CarService carService;


    @GetMapping("/paged")
    public List<ReturnCarDTO> getPagedReturnCars(
            @RequestParam(value = "returnNo", required = false) Long returnNo,
            @RequestParam(value = "carName", required = false) String carName) {
        return returnService.getReturnCarList(returnNo, carName);
    }
    @DeleteMapping("/{id}")
    public Result<Integer> deleteReturn(@PathVariable Long id) {
        int result = returnService.deleteReturn(id);
        return result > 0 ?
                new Result<Integer>().code(0).message("删除成功").data(result) :
                new Result<Integer>().code(-1).message("删除失败").data(0);
    }

    @DeleteMapping("/batch")
    public Result<Integer> batchDeleteReturn(@RequestBody List<Long> ids) {
        int result = returnService.batchDeleteReturn(ids);
        return result > 0 ?
                new Result<Integer>().code(0).message("批量删除成功").data(result) :
                new Result<Integer>().code(-1).message("批量删除失败").data(0);
    }

    @PutMapping("/{returnId}/return")
    @Transactional
    public Result<?> returnCar(@PathVariable Long returnId) {
        try {
            // 1. 获取订单
            Order order = orderService.getById(returnId);
            if (order == null) {
                return Result.fail("归还记录不存在");
            }

            // 2. 检查车辆状态
            if (order.getCarStatus() == 1) {
                return Result.fail("车辆已归还");
            }

            // 3. 更新订单
            order.setCarStatus(1);
            order.setUpdateTime(new Date());
            if (!orderService.updateOrder(order)) {
                throw new RuntimeException("订单状态更新失败");
            }

            // 4. 更新车辆状态（需要实现CarService）
            Car car = carService.getCarById(order.getCarId());
            if (car != null) {
                car.setStatus(Car.STATUS_AVAILABLE);
                car.setUserid(null);
                carService.updateCar(car); // 需要实现该方法
            }

            return Result.success("还车成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("还车操作失败: {}", e.getMessage());
            return Result.fail("系统错误: " + e.getMessage());
        }
    }


}
