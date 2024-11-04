package com.yukuii.powerbank.auth.service;

import com.yukuii.powerbank.auth.dto.LoginDTO;

import cn.dev33.satoken.stp.SaTokenInfo;

public interface AuthService {

    SaTokenInfo adminLogin(LoginDTO loginDTO);

    SaTokenInfo userLogin(LoginDTO loginDTO);
        
}
