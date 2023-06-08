package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.util.PhoneUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.dto.CaptchaRequest;
import com.imyuanxiao.rbac.model.dto.LoginRequest;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.CommonConst;
import com.imyuanxiao.rbac.util.ValidationGroups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
    * @param registerRequest register-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/register")
    @ApiOperation(value = "Register")
    public String register(@RequestBody @Valid LoginRequest registerRequest){
        dataValidation(registerRequest);
        return userService.register(registerRequest);
    }

    private void dataValidation(LoginRequest request) {
        String type = request.getType();
        if (CommonConst.ACCOUNT.equals(type)) {
            // 执行账号登录的验证逻辑
            validator.validate(request, ValidationGroups.Account.class);
        } else if (CommonConst.MOBILE.equals(type)) {
            // 执行手机号登录的验证逻辑
            validator.validate(request, ValidationGroups.Mobile.class);
        } else {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
    }

    /**
    * @description: login
    * @param loginRequest login-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/login")
    @ApiOperation(value = "Login")
    public UserVO login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request){
        dataValidation(loginRequest);
        return userService.login(loginRequest, request);
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
