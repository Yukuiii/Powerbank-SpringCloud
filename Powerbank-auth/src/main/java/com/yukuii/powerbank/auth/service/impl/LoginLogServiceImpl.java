package com.yukuii.powerbank.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukuii.powerbank.auth.mapper.LoginLogMapper;
import com.yukuii.powerbank.auth.model.LoginLog;
import com.yukuii.powerbank.auth.service.LoginLogService;
import com.yukuii.powerbank.auth.utils.LoginInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    public void recordLoginLog(String userId, String username) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            
            LoginLog loginLog = new LoginLog();
            // 设置ID和登录时间
            loginLog.setId(UUID.randomUUID().toString().replace("-", ""));
            loginLog.setLoginTime(LocalDateTime.now());
            loginLog.setUserId(userId);
            loginLog.setUsername(username);
            
            // 设置IP和登录地点
            String ipAddr = LoginInfoUtil.getIpAddr(request);
            loginLog.setLoginIp(ipAddr);
            
            // 设置浏览器和操作系统信息
            loginLog.setBrowserType(LoginInfoUtil.getBrowserType(request));
            loginLog.setOperatingSystem(LoginInfoUtil.getOperatingSystem(request));
            
            // 保存登录日志
            this.save(loginLog);
        } catch (Exception e) {
            log.error("记录登录日志失败", e);
        }
    }

} 