package com.yukuii.powerbank.order.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yukuii.powerbank.common.exception.BizException;
import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.order.dto.CreateOrderDTO;
import com.yukuii.powerbank.order.mapper.OrderMapper;
import com.yukuii.powerbank.order.model.Order;
import com.yukuii.powerbank.order.service.OrderService;

import jakarta.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(CreateOrderDTO createOrderDTO) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(createOrderDTO.getUserId());
        order.setDeviceId(createOrderDTO.getDeviceId());
        order.setStatus(0); // 未支付
        order.setAmount(new BigDecimal("10.00")); // 暂时固定金额，后续可以根据设备类型定价
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setRemark(createOrderDTO.getRemark());
        
        int rows = orderMapper.insert(order);
        if (rows != 1) {
            throw new BizException("创建订单失败");
        }
        
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        
        if (order.getStatus() != 0) {
            throw new BizException("订单状态不正确");
        }
        
        int rows = orderMapper.updatePayInfo(orderId, 1);
        if (rows != 1) {
            throw new BizException("支付失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        
        if (order.getStatus() != 0) {
            throw new BizException("订单状态不正确");
        }
        
        int rows = orderMapper.updateStatus(orderId, 4);
        if (rows != 1) {
            throw new BizException("取消订单失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeOrder(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        
        if (order.getStatus() != 2) {
            throw new BizException("订单状态不正确");
        }
        
        int rows = orderMapper.updateReturnInfo(orderId);
        if (rows != 1) {
            throw new BizException("完成订单失败");
        }
    }

    @Override
    public Order getOrderInfo(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        return order;
    }

    @Override
    public PageResult<Order> getOrderList(Integer pageNum, Integer pageSize, Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("status", status);
        
        List<Order> orders = orderMapper.findByPage(params);
        long total = orderMapper.countTotal(params);
        
        return new PageResult<>(total, orders);
    }

    @Override
    public List<Order> getUserOrders(String userId) {
        return orderMapper.findByUserId(userId);
    }
} 