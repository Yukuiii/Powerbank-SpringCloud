package com.yukuii.powerbank.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yukuii.powerbank.order.model.Order;

@Mapper
public interface OrderMapper {
    
    int insert(Order order);
    
    Order findById(String id);
    
    int updateStatus(String id, Integer status);
    
    int updatePayInfo(String id, Integer status);
    
    int updateReturnInfo(String id);
    
    List<Order> findByPage(Map<String, Object> params);
    
    long countTotal(Map<String, Object> params);
    
    List<Order> findByUserId(String userId);
} 