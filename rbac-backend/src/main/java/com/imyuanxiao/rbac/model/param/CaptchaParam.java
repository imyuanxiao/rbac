package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @description receive phone or email
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class CaptchaParam {
    @NotBlank(message = "Phone is required.")
    @Length(min = 4, max = 20, message = "Account must be between 4-20 characters in length.")
    @ExceptionCode(value = 100002, message = "Invalid phone.")
    private String phone;

}
