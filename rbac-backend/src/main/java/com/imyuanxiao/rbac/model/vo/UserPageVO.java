package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @description  User pagination object.
 * @author  imyuanxiao
 **/
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private Set<Long> roleIds;
    private Set<Long> orgIds;
}
