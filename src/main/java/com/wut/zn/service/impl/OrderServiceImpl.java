package com.wut.zn.service.impl;

import com.wut.zn.entity.Order;
import com.wut.zn.entity.dto.OrderDTO;
import com.wut.zn.entity.dto.OrderVO;
import com.wut.zn.mapper.OrderMapper;
import com.wut.zn.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wut.zn.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;




    public Result<List<OrderVO>> getOrderList(String orderNo, String carName) {
        List<OrderVO> orders = orderMapper.selectOrderList(orderNo, carName);
        return Result.success(orders);
    }

    public int getTotalCount() {
        return orderMapper.countAllOrders();
    }

    // 单个删除
    @Transactional
    public int deleteOrder(Long id) {
        return orderMapper.deleteById(id);
    }

    // 批量删除
    @Transactional
    public int batchDeleteOrders(List<Long> ids) {
        return orderMapper.batchDelete(ids);
    }


    @Override
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }



    @Override
    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public boolean updateById(Order order) {
        return orderMapper.updateById(order) > 0;
    }

    @Override
    @Transactional // 添加事务管理
    public boolean updateOrder(Order order) {
        try {
            // 返回受影响的行数，>0 表示成功
            return orderMapper.update(order) > 0;
        } catch (Exception e) {
            throw new RuntimeException("订单更新异常: " + e.getMessage());
        }
    }
}
