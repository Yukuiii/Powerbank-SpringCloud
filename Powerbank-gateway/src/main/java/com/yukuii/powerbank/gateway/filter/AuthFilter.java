package com.yukuii.powerbank.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    // 白名单路径
    private static final List<String> WHITE_LIST = List.of(
            "/auth/login",
            "/auth/register",
            "/user/register",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/api-docs/**"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // 白名单放行
        if (isWhitePath(path)) {
            return chain.filter(exchange);
        }
        
        // 检查是否登录
        SaRouter.match("/**")
            .check(r -> StpUtil.checkLogin());
            
        return chain.filter(exchange);
    }

    private boolean isWhitePath(String path) {
        return WHITE_LIST.stream().anyMatch(pattern -> 
            path.startsWith(pattern) || path.matches(pattern.replace("/**", "/.*")));
    }

    @Override
    public int getOrder() {
        return -100;
    }
} 