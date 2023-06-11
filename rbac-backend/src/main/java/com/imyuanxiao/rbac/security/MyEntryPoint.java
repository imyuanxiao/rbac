package com.imyuanxiao.rbac.security;

import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description  认证异常处理
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
public class MyEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // Use attribute to return different message
        String errorMessage = (String)request.getAttribute("errorMessage");
        ResultVO<String> resultVO = errorMessage != null ?
                new ResultVO<>(ResultCode.ACCOUNT_TAKEOVER,  errorMessage) :
                new ResultVO<>(ResultCode.UNAUTHORIZED,  "Please log in") ;

        log.error(e.getMessage() + errorMessage);
        out.write(resultVO.toString());
        out.flush();
        out.close();
    }
}