package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @ClassName UserPageVO
 * @Description User pagination object.
 * @Author imyuanxiao
 * @Date 2023/5/4 15:18
 * @Version 1.0
 **/
@Data
public class UserPageVO {
    private Long id;
    private String userAccount;
    private Set<Long> roleIds;
}
