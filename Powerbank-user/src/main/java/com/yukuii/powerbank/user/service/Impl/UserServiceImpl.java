package com.yukuii.powerbank.user.service.Impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yukuii.powerbank.common.exception.BizException;
import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.user.dto.RegisterDTO;
import com.yukuii.powerbank.user.dto.UpdatePasswordDTO;
import com.yukuii.powerbank.user.dto.UpdateUserDTO;
import com.yukuii.powerbank.user.mapper.UserMapper;
import com.yukuii.powerbank.user.model.User;
import com.yukuii.powerbank.user.service.UserService;
import com.yukuii.powerbank.user.utils.IpUtil;

import cn.hutool.crypto.digest.BCrypt;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new BizException("用户名已存在");
        }

        // 创建用户实体
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);

        // 设置其他属性
        user.setId(UUID.randomUUID().toString());
        user.setPassword(BCrypt.hashpw(registerDTO.getPassword()));
        user.setUserType(0); // 设置为普通用户
        user.setStatus(1); // 设置为启用状态
        user.setLastLoginIp(IpUtil.getIpAddr(request)); // 设置注册IP
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now()); // 设置注册时间为最后登录时间

        // 保存用户
        userMapper.insert(user);
    }

    @Override
    public User getUserInfo(String userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        // 出于安全考虑，清除敏感信息
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        // 验证新密码与确认密码是否一致
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmPassword())) {
            throw new BizException("新密码与确认密码不一致");
        }

        // 获取用户信息
        User user = userMapper.findById(updatePasswordDTO.getUserId());
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 验证原密码是否正确
        if (!BCrypt.checkpw(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new BizException("原密码错误");
        }

        // 验证新密码是否与原密码相同
        if (updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())) {
            throw new BizException("新密码不能与原密码相同");
        }

        // 更新密码
        String newPasswordHash = BCrypt.hashpw(updatePasswordDTO.getNewPassword());
        int rows = userMapper.updatePassword(user.getId(), newPasswordHash, LocalDateTime.now());
        if (rows != 1) {
            throw new BizException("修改密码失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UpdateUserDTO updateUserDTO) {
        User user = userMapper.findById(updateUserDTO.getId());
        if (user == null) {
            throw new BizException("用户不存在");
        }
        
        User updateUser = new User();
        BeanUtils.copyProperties(updateUserDTO, updateUser);
        updateUser.setUpdateTime(LocalDateTime.now());
        
        int rows = userMapper.updateUserInfo(updateUser);
        if (rows != 1) {
            throw new BizException("更新用户信息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String userId, Integer status) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        
        if (!Arrays.asList(0, 1).contains(status)) {
            throw new BizException("状态值不正确");
        }
        
        int rows = userMapper.updateStatus(userId, status, LocalDateTime.now());
        if (rows != 1) {
            throw new BizException("更新用户状态失败");
        }
    }

    @Override
    public PageResult<User> getUserList(Integer pageNum, Integer pageSize, String username, Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("username", username);
        params.put("status", status);
        
        List<User> users = userMapper.findByPage(params);
        long total = userMapper.countTotal(params);
        
        // 清除敏感信息
        users.forEach(user -> user.setPassword(null));
        
        return new PageResult<>(total, users);
    }
}