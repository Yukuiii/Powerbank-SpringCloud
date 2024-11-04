package com.yukuii.powerbank.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public String getUserInfo() {
        return "user info";
    }

}
