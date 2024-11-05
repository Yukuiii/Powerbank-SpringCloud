package com.yukuii.powerbank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "更新用户信息DTO")
public class UpdateUserDTO {
    
    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private String id;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号")
    private String mobile;
    
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "备注")
    private String remark;
} 