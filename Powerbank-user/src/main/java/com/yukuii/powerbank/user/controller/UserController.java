package com.yukuii.powerbank.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yukuii.powerbank.common.pojo.CommonResult;
import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.user.dto.RegisterDTO;
import com.yukuii.powerbank.user.dto.UpdatePasswordDTO;
import com.yukuii.powerbank.user.dto.UpdateUserDTO;
import com.yukuii.powerbank.user.model.User;
import com.yukuii.powerbank.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/info/{userId}")
    @Operation(summary = "获取用户信息")
    public CommonResult<User> getUserInfo(
            @Parameter(description = "用户ID") 
            @PathVariable String userId) {
        User user = userService.getUserInfo(userId);
        return CommonResult.success(user);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public CommonResult<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return CommonResult.success("注册成功");
    }

    @PostMapping("/password/update")
    @Operation(summary = "修改密码")
    public CommonResult<String> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(updatePasswordDTO);
        return CommonResult.success("密码修改成功");
    }

    @PostMapping("/info/update")
    @Operation(summary = "更新用户信息")
    public CommonResult<String> updateUserInfo(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUserInfo(updateUserDTO);
        return CommonResult.success("更新成功");
    }

    @PostMapping("/status/{userId}/{status}")
    @Operation(summary = "更新用户状态")
    public CommonResult<String> updateStatus(
            @Parameter(description = "用户ID") @PathVariable String userId,
            @Parameter(description = "状态(1:启用 0:禁用)") @PathVariable Integer status) {
        userService.updateStatus(userId, status);
        return CommonResult.success("状态更新成功");
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询用户列表")
    public CommonResult<PageResult<User>> getUserList(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        return CommonResult.success(userService.getUserList(pageNum, pageSize, username, status));
    }
}
