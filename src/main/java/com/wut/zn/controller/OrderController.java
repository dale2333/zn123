package com.wut.zn.controller;

import com.wut.zn.entity.Car;
import com.wut.zn.entity.Order;
import com.wut.zn.entity.User;
import com.wut.zn.entity.dto.OrderDTO;
import com.wut.zn.entity.dto.OrderVO;
import com.wut.zn.service.CarService;
import com.wut.zn.service.OrderService;
import com.github.pagehelper.Page;
import com.wut.zn.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @GetMapping("/paged")
    public Result<List<OrderVO>> getPagedOrders(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String carName) {
        return orderService.getOrderList(orderNo, carName);
    }

    @DeleteMapping("/{id}")
    public Result<Integer> deleteOrder(@PathVariable Long id) {
        int result = orderService.deleteOrder(id);
        return result > 0 ?
                new Result<Integer>().code(0).message("删除成功").data(result) :
                new Result<Integer>().code(-1).message("删除失败").data(0);
    }

    @DeleteMapping("/batch")
    public Result<Integer> batchDeleteOrders(@RequestBody List<Long> ids) {
        int result = orderService.batchDeleteOrders(ids);
        return result > 0 ?
                new Result<Integer>().code(0).message("批量删除成功").data(result) :
                new Result<Integer>().code(-1).message("批量删除失败").data(0);
    }


    @PostMapping("/create")
    @Transactional
    public Result<?> createOrder(@RequestBody Order order, HttpSession session) {
        try {
            // 1. 验证登录
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser == null) {
                return Result.fail("用户未登录");
            }

            // 2. 获取车辆信息
            Car car = carService.getCarById(order.getCarId());
            if (car == null) {
                return Result.fail("车辆不存在");
            }

            // 3. 检查车辆状态
            if (car.getStatus() == Car.STATUS_RENTED) {
                return Result.fail("该车辆已被租用");
            }

            // 4. 创建订单（增加字段处理）
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setDays(order.getDays()); // 明确设置天数
            order.setUserId(currentUser.getId());
            order.setCarStatus(0); // 设置初始状态，0表示订单创建
            orderService.createOrder(order);

            // 5. 更新车辆状态（新增userid处理）
            car.setUserid(currentUser.getId()); // 将租赁人ID写入car表
            car.setStatus(Car.STATUS_RENTED);
            car.setUpdateTime(new Date()); // 添加更新时间
            carService.updateCar(car);

            return Result.success()
                    .message("租赁成功")
                    .data(order.getId()); // 返回订单ID

        } catch (Exception e) {
            log.error("租赁失败: {}", e.getMessage());
            return Result.fail("租赁流程异常: " + e.getMessage());
        }
    }
}
