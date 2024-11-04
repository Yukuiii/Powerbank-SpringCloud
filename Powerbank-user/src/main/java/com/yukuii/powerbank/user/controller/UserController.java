package com.yukuii.powerbank.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yukuii.powerbank.common.pojo.CommonResult;
import com.yukuii.powerbank.user.dto.RegisterDTO;
import com.yukuii.powerbank.user.dto.UpdatePasswordDTO;
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
}
