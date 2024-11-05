package com.yukuii.powerbank.user.service;

import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.user.dto.RegisterDTO;
import com.yukuii.powerbank.user.dto.UpdatePasswordDTO;
import com.yukuii.powerbank.user.dto.UpdateUserDTO;
import com.yukuii.powerbank.user.model.User;

public interface UserService {
    void register(RegisterDTO registerDTO);
    
    User getUserInfo(String userId);
    
    void updatePassword(UpdatePasswordDTO updatePasswordDTO);
    
    void updateUserInfo(UpdateUserDTO updateUserDTO);
    
    void updateStatus(String userId, Integer status);
    
    PageResult<User> getUserList(Integer pageNum, Integer pageSize, String username, Integer status);
} 