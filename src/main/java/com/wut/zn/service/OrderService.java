package com.wut.zn.service;

import com.wut.zn.entity.Order;
import com.wut.zn.entity.dto.OrderDTO;
import com.github.pagehelper.Page;
import com.wut.zn.entity.dto.OrderVO;
import com.wut.zn.utils.Result;

import java.util.List;

public interface OrderService {


    int getTotalCount();
    Result<List<OrderVO>> getOrderList(String orderNo, String carName);

    int batchDeleteOrders(List<Long> ids);
    int deleteOrder(Long id);


    void createOrder(Order order);


    Order getById(Long id);
    boolean updateById(Order order);

    boolean updateOrder(Order order);

}
