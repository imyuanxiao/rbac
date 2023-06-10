package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @description  Role pagination object.
 * @author  imyuanxiao
 **/
@Data
public class RolePageVO {
    private Long id;
    private String roleName;
    private String description;
    private Set<Long> permissionIds;
}
