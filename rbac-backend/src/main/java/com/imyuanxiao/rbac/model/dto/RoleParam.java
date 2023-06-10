package com.imyuanxiao.rbac.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.imyuanxiao.rbac.util.ValidationGroups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @description  Receive role-related parameters.
 * @author  imyuanxiao
 **/
@Data
public class RoleParam {
    @NotNull(message = "User ID is required", groups = ValidationGroups.UpdateResources.class)
    private Long id;

    @NotBlank(message = "Role name is required", groups = ValidationGroups.CreateRole.class)
    @Length(min = 4, max = 20, message = "Role name must be between 4-20 characters in length.", groups = ValidationGroups.CreateRole.class)
    private String roleName;

    private String description;

    private Set<Long> permissionIds;

}