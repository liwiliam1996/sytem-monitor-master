package com.monitor.master.controller;

import com.monitor.master.common.R;
import com.monitor.master.domain.LoginUser;
import com.monitor.master.service.TokenService;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lierqiang
 */
@RequestMapping("/system")
@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public R login(String username, String password) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = null;

        String token = null;

        try {
            authenticate = this.authenticationManager.authenticate(authenticationToken);

            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

            token = tokenService.createToken(loginUser);
        } catch (BadCredentialsException badCredentialsException) {
            log.error(badCredentialsException.getMessage());
            return R.error(badCredentialsException.getMessage());
        }

        return R.ok(token);
    }

    @GetMapping("/resource")
    public R resource() {
        return R.ok("请求资源成功");
    }


}
