package com.imyuanxiao.rbac.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/10 13:02
 **/
@Data
public class UserListRequest {

    @NotNull(message = "current is required.")
    private Integer current;

    @NotNull(message = "pageSize is required.")
    private Integer pageSize;

    private String username;
    private Integer userStatus;
    private Set<Long> roleIds;

}
