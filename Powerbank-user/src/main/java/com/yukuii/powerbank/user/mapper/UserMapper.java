package com.yukuii.powerbank.user.mapper;

import com.yukuii.powerbank.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
        
    User findByUsername(String username);
        
    int insert(User user);
    
    User findById(String id);
    
    int updatePassword(String id, String newPassword, LocalDateTime updateTime);
} 