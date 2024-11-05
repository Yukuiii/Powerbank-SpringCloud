package com.yukuii.powerbank.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "创建订单DTO")
public class CreateOrderDTO {
    
    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private String userId;
    
    @NotBlank(message = "设备ID不能为空")
    @Schema(description = "设备ID")
    private String deviceId;
    
    @Schema(description = "备注")
    private String remark;
} 