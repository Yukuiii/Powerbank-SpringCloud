package com.yukuii.powerbank.order.service;

import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.order.dto.CreateOrderDTO;
import com.yukuii.powerbank.order.model.Order;

public interface OrderService {
    
    Order createOrder(CreateOrderDTO createOrderDTO);
    
    void payOrder(String orderId);
    
    void cancelOrder(String orderId);
    
    void completeOrder(String orderId);
    
    Order getOrderInfo(String orderId);
    
    PageResult<Order> getOrderList(Integer pageNum, Integer pageSize, Integer status);
    
    List<Order> getUserOrders(String userId);
} 