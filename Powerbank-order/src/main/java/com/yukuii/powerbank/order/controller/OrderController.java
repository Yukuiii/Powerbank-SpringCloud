package com.yukuii.powerbank.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yukuii.powerbank.common.pojo.CommonResult;
import com.yukuii.powerbank.common.pojo.PageResult;
import com.yukuii.powerbank.order.dto.CreateOrderDTO;
import com.yukuii.powerbank.order.model.Order;
import com.yukuii.powerbank.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
@Tag(name = "订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "创建订单")
    public CommonResult<Order> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO) {
        return CommonResult.success(orderService.createOrder(createOrderDTO));
    }

    @PostMapping("/pay/{orderId}")
    @Operation(summary = "支付订单")
    public CommonResult<String> payOrder(
            @Parameter(description = "订单ID") @PathVariable String orderId) {
        orderService.payOrder(orderId);
        return CommonResult.success("支付成功");
    }

    @PostMapping("/cancel/{orderId}")
    @Operation(summary = "取消订单")
    public CommonResult<String> cancelOrder(
            @Parameter(description = "订单ID") @PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return CommonResult.success("取消成功");
    }

    @PostMapping("/complete/{orderId}")
    @Operation(summary = "完成订单")
    public CommonResult<String> completeOrder(
            @Parameter(description = "订单ID") @PathVariable String orderId) {
        orderService.completeOrder(orderId);
        return CommonResult.success("订单已完成");
    }

    @GetMapping("/info/{orderId}")
    @Operation(summary = "获取订单信息")
    public CommonResult<Order> getOrderInfo(
            @Parameter(description = "订单ID") @PathVariable String orderId) {
        return CommonResult.success(orderService.getOrderInfo(orderId));
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询订单列表")
    public CommonResult<PageResult<Order>> getOrderList(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "订单状态") @RequestParam(required = false) Integer status) {
        return CommonResult.success(orderService.getOrderList(pageNum, pageSize, status));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户订单列表")
    public CommonResult<List<Order>> getUserOrders(
            @Parameter(description = "用户ID") @PathVariable String userId) {
        return CommonResult.success(orderService.getUserOrders(userId));
    }
} 