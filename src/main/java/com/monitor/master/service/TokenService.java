package com.monitor.master.service;

import com.monitor.master.domain.LoginUser;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    public String createToken(LoginUser loginUser);

    public void refreshToken(LoginUser loginUser);

    public void verifyToken(LoginUser loginUser);

    public LoginUser getLoginUser(HttpServletRequest request);


}
