package com.yukuii.powerbank.user.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yukuii.powerbank.user.model.User;

@Mapper
public interface UserMapper {
        
    User findByUsername(String username);
        
    int insert(User user);
    
    User findById(String id);
    
    int updatePassword(String id, String newPassword, LocalDateTime updateTime);
    
    int updateUserInfo(User user);
    
    int updateStatus(String id, Integer status, LocalDateTime updateTime);
    
    List<User> findByPage(Map<String, Object> params);
    
    long countTotal(Map<String, Object> params);
} 