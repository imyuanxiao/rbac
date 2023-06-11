package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import com.imyuanxiao.rbac.util.ValidationGroups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @description  Receive login-related parameters.
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class LoginRequestParam {

    @NotBlank
    @Pattern(regexp = "^(account|mobile)$", message = "Invalid request.")
    @ExceptionCode(value = 100006, message = "Invalid request.")
    private String type;

    @NotBlank(message = "Phone number is required.", groups = {ValidationGroups.Mobile.class})
    private String mobile;

    @NotBlank(message = "Captcha is required.", groups = {ValidationGroups.Mobile.class})
    @Pattern(regexp = "^\\d{4}$", message = "Invalid captcha.", groups = {ValidationGroups.Mobile.class})
    private String captcha;

    @NotBlank(message = "Username is required.", groups = {ValidationGroups.Account.class})
    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters in length.", groups = {ValidationGroups.Account.class})
    @ExceptionCode(value = 100002, message = "Invalid username.")
    private String username;

    @NotBlank(message = "Password is required.", groups = {ValidationGroups.Account.class})
    @Length(min = 4, max = 20, message = "Password must be between 4-20 characters in length.", groups = {ValidationGroups.Account.class})
    @ExceptionCode(value = 100002, message = "Invalid password.")
    private String password;

}
