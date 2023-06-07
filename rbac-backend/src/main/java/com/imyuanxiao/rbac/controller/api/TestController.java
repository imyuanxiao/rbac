package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.security.JwtManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName TestController
 * @Description 测试接口
 * @Author imyuanxiao
 * @Date 2023/5/4 17:51
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/test")
@Api(tags = "Test Interface")
public class TestController {

    @GetMapping("/takeover")
    @ApiOperation(value = "测试被顶号登录")
    public String testHasAuth() {
        return "测试成功，你有权限";
    }

    @ApiOperation(value = "测试无权限")
    @GetMapping("/2")
    public String testNoAuth() {
        return "测试成功，你有权限";
    }

    @ApiOperation(value = "根据传入参数生成token")
    @GetMapping("/token-generate/{username}")
    public String testTokenGenerate(@PathVariable("username") String username) {
        // 生成token并返回
        return JwtManager.generate(username);
    }

    @ApiOperation(value = "测试token解析，需在请求头中加入token")
    @GetMapping("/token-verify")
    public String testTokenVerify() {
        // 解析成功就执行业务逻辑返回数据
        return "api成功返回数据";
    }


}
