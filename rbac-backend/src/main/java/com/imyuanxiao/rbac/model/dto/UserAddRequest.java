package com.imyuanxiao.rbac.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName UserParam
 * @Description Receive user-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/7 11:12
 * @Version 1.0
 **/
@Data
public class UserAddRequest {
    @NotNull(message = "UserID is required.", groups = Update.class)
    private Long id;

    @NotBlank(message = "Account is required.", groups = CreateUser.class)
    @Length(min = 4, max = 20, message = "Account must be between 4-20 characters in length.")
    @Email(message = "Invalid account.")
    private String account;

    private List<Long> roleIds;

    public interface Update {}

    public interface CreateUser{}
}