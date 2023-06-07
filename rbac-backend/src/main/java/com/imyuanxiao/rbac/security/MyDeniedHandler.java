package com.imyuanxiao.rbac.security;

import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @ClassName MyDeniedHandler
 * @Description 返回无权限信息
 * @Author imyuanxiao
 * @Date 2023/5/6 11:18
 * @Version 1.0
 **/
public class MyDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ResultVO<String> resultVO = new ResultVO<>(ResultCode.FORBIDDEN, "没有相关权限");
        out.write(resultVO.toString());
        out.flush();
        out.close();
    }
}