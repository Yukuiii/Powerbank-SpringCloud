package com.yukuii.powerbank.auth.service.impl;

import com.yukuii.powerbank.auth.dto.LoginDTO;
import com.yukuii.powerbank.auth.service.AuthService;
import com.yukuii.powerbank.common.exception.BizException;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yukuii.powerbank.auth.model.Admin;
import com.yukuii.powerbank.auth.model.User;
import com.yukuii.powerbank.auth.mapper.AuthAdminMapper;
import com.yukuii.powerbank.auth.mapper.AuthUserMapper;
import com.yukuii.powerbank.auth.constant.AuthConstant;
import com.yukuii.powerbank.auth.service.LoginLogService;
import cn.hutool.crypto.digest.BCrypt;

import cn.dev33.satoken.stp.StpUtil;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private AuthAdminMapper adminMapper;
    private AuthUserMapper userMapper;
    private LoginLogService loginLogService;

    @Override
    public SaTokenInfo adminLogin(LoginDTO loginDTO) {
        if (StrUtil.isEmpty(loginDTO.getUsername()) || StrUtil.isEmpty(loginDTO.getPassword())) {
            throw new BizException("用户名或密码不能为空");
        }
        Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, loginDTO.getUsername()));

        if (admin == null) {
            throw new BizException("找不到该用户");
        }

        if (admin.getStatus() == 0) {
            throw new BizException("该用户已被禁用");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), admin.getPassword())) {
            throw new BizException("密码不正确");
        }

        StpUtil.login(admin.getId());
        loginLogService.recordLoginLog(admin.getId(), admin.getUsername());
        StpUtil.getSession().set(AuthConstant.STP_ADMIN_INFO, admin);
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        return saTokenInfo;

    }

    @Override
    public SaTokenInfo userLogin(LoginDTO loginDTO) {

        if (StrUtil.isEmpty(loginDTO.getUsername()) || StrUtil.isEmpty(loginDTO.getPassword())) {
            throw new BizException("用户名或密码不能为空");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDTO.getUsername()));

        if (user == null) {
            throw new BizException("找不到该用户");
        }

        if (user.getStatus() == 0) {
            throw new BizException("该用户已被禁用");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            throw new BizException("密码不正确");
        }

        StpUtil.login(user.getId());
        loginLogService.recordLoginLog(user.getId(), user.getUsername());
        StpUtil.getSession().set(AuthConstant.STP_USER_INFO, user);
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        return saTokenInfo;
    }

}
