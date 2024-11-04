package com.yukuii.powerbank.common.enums;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统错误"),
    PARAM_ERROR(400, "参数错误");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 