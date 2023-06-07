package com.imyuanxiao.rbac.model.dto;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName LoginParam
 * @Description Receive login-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/3 0:58
 * @Version 1.0
 **/
@Data
public class LoginRequest {

    @NotBlank(message = "Account is required.")
    @Length(min = 4, max = 20, message = "Account must be between 4-20 characters in length.")
    @ExceptionCode(value = 100002, message = "Invalid account.")
    private String account;

    private String password;

    private String captcha;

    @NotBlank
    @Pattern(regexp = "^(phone|password)$", message = "Invalid request.")
    @ExceptionCode(value = 100006, message = "Invalid request.")
    private String loginType;

}
