package com.yukuii.powerbank.common.pojo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> {
    
    @Schema(description = "总记录数")
    private long total;
    
    @Schema(description = "数据列表")
    private List<T> list;
} 