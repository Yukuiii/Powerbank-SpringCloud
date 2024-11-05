package com.yukuii.powerbank.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yukuii.powerbank.auth.dto.LoginDTO;
import com.yukuii.powerbank.common.pojo.CommonResult;
import com.yukuii.powerbank.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import cn.dev33.satoken.stp.SaTokenInfo;

@RestController
@AllArgsConstructor
@Tag(description = "统一认证授权接口", name = "AuthController")
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Operation(summary = "认证授权接口,返回token", description = "用户登录认证接口,验证用户名和密码,成功后返回JWT token", responses = {
            @ApiResponse(responseCode = "200", description = "认证成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "500", description = "系统错误")
    })
    @PostMapping("/login")
    public CommonResult<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        Integer userType = loginDTO.getUserType();
        SaTokenInfo saTokenInfo;
        if (userType == 0) {
            saTokenInfo = authService.adminLogin(loginDTO);
        } else {
            saTokenInfo = authService.userLogin(loginDTO);
        }
        return CommonResult.success(saTokenInfo.getTokenValue());
    }

    @Operation(summary = "退出登录接口", description = "用户退出登录接口，清除token信息", responses = {
            @ApiResponse(responseCode = "200", description = "退出成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "500", description = "系统错误")
    })
    @PostMapping("/logout")
    public CommonResult<String> logout() {
        authService.logout();
        return CommonResult.success("退出成功");
    }
}
