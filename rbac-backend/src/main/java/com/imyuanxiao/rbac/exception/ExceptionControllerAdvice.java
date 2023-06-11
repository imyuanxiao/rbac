package com.imyuanxiao.rbac.exception;


import com.imyuanxiao.rbac.annotation.ExceptionCode;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.AccountTakeoverException;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

/**
 * @description  Global exception handler
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 处理自定义的ApiException异常
     * @author imyuanxiao
     * @date 20:33 2023/5/6
     * @param e ApiException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(ApiException.class)
    public ResultVO<String> apiExceptionHandler(ApiException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(e.getResultCode(), e.getMsg());
    }

    /**
     *
     * @author imyuanxiao
     * @date 20:33 2023/5/6
     * @param e MethodArgumentNotValidException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
            throws NoSuchFieldException{

        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        Class<?> parameterType = e.getParameter().getParameterType();

        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = parameterType.getDeclaredField(fieldName);
        ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);

        if(annotation != null){
            return new ResultVO<>(annotation.value(), annotation.message(), defaultMessage);
        }

        return new ResultVO<>(ResultCode.VALIDATE_FAILED, defaultMessage);
    }

    /**
     * 封装处理运行时发生的其他异常
     * @author imyuanxiao
     * @date 20:32 2023/5/6
     * @param e RuntimeException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<String> runtimeExceptionHandler(RuntimeException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(ResultCode.ERROR, "系统异常，请稍后重试");
    }

    /**
     * 处理 LoginFilter 抛出的 AccountTakeoverException 异常
     */
    @ExceptionHandler(AccountTakeoverException.class)
    public ResultVO<String> accountTakeoverExceptionHandler(AccountTakeoverException e) {
        return new ResultVO<>(ResultCode.UNAUTHORIZED, e.getMessage());
    }

}
