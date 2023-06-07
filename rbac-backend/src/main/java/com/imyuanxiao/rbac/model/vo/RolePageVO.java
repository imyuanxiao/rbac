package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @ClassName RolePageVO
 * @Description Role pagination object.
 * @Author imyuanxiao
 * @Date 2023/5/4 15:18
 * @Version 1.0
 **/
@Data
public class RolePageVO {
    private Long id;
    private String name;
    private Set<Long> permissionIds;
}
