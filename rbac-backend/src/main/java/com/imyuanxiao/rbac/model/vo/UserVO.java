package com.imyuanxiao.rbac.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @description  User Value Object
 * @author  imyuanxiao
 **/
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

    private String avatar;

    private String nickName;

    private Set<Long> roleIds;

    private Set<Long> permissionIds;

}
