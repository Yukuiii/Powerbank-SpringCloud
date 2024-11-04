package com.yukuii.powerbank.user.service;

import com.yukuii.powerbank.user.dto.RegisterDTO;
import com.yukuii.powerbank.user.dto.UpdatePasswordDTO;
import com.yukuii.powerbank.user.model.User;

public interface UserService {
    void register(RegisterDTO registerDTO);
    
    User getUserInfo(String userId);
    
    void updatePassword(UpdatePasswordDTO updatePasswordDTO);
} 