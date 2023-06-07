package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.util.PhoneUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.dto.CaptchaRequest;
import com.imyuanxiao.rbac.model.dto.LoginRequest;
import com.imyuanxiao.rbac.model.dto.RegisterRequest;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/**
 * @description  登录注册接口
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@Api(tags = "Auth Management Interface")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
    * @description: show captcha in frontend (actually should be void)
    * @param request phone
    * @author imyuanxiao
    */
    @PostMapping("/captcha")
    @ApiOperation(value = "Get Captcha")
    public String sendCaptcha(@RequestBody CaptchaRequest request){
        String phone = request.getPhone();
        if(!PhoneUtil.isPhone(phone)){
            throw new ApiException(ResultCode.PARAMS_ERROR,"手机号格式错误！");
        }
        return userService.sendCaptcha(phone);
    }

    /**
    * @description: register
    * @param request register-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/register")
    @ApiOperation(value = "Register")
    public String register(@RequestBody @Valid RegisterRequest request){
        return userService.register(request);
    }

    /**
    * @description: login
    * @param param login-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/login")
    @ApiOperation(value = "Login")
    public UserVO login(@RequestBody @Valid LoginRequest param, HttpServletRequest request){
        return userService.login(param, request);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "Logout")
    public void logout(HttpServletRequest request){ userService.logout(request);}

    @GetMapping("/update-token")
    @ApiOperation(value = "Update token")
    public String updateToken(){
        return userService.updateToken();
    }

    @GetMapping("/my-permission")
    @ApiOperation(value = "Get UserVO every time route changes")
    public Set<Long> myPermission(){
        return userService.myPermission();
    }

}
