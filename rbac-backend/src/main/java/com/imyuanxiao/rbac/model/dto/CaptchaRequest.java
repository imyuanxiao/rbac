package com.imyuanxiao.rbac.model.dto;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @description receive phone or email
 * @author: imyuanxiao
 * @date: 2023/6/7 21:07
 **/
@Data
public class CaptchaRequest {
    @NotBlank(message = "Phone is required.")
    @Length(min = 4, max = 20, message = "Account must be between 4-20 characters in length.")
    @ExceptionCode(value = 100002, message = "Invalid phone.")
    private String phone;

}
