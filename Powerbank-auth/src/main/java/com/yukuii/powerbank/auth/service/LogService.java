package com.yukuii.powerbank.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukuii.powerbank.auth.model.LoginLog;

public interface LogService extends IService<LoginLog> {
    
    /**
     * 记录登录日志
     * @param loginLog 登录日志信息
     */
    void recordLoginLog(String userId, String username);

    /**
     * 记录登出日志
     * @param userId 用户ID
     */
    void recordLogoutLog(Long userId, String username);

} 