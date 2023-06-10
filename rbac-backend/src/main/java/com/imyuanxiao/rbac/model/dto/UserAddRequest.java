package com.imyuanxiao.rbac.model.dto;

import com.imyuanxiao.rbac.util.ValidationGroups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 该请求应该比分页数据多一些用户详细信息
 * @description  Receive user-related parameters.
 * @author  imyuanxiao
 **/
@Data
public class UserAddRequest {

    @NotNull(message = "UserID is required.", groups = ValidationGroups.UpdateUser.class)
    private Long id;

    @NotBlank(message = "Username is required.", groups = ValidationGroups.CreateUser.class)
    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters in length.")
    @Email(message = "Invalid username.")
    private String username;

    private Integer userStatus;

    private Set<Long> roleIds;

    private Set<Long> orgIds;

}