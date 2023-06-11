package com.imyuanxiao.rbac.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class UserPageParam {

    @NotNull(message = "current is required.")
    private Integer current;

    @NotNull(message = "pageSize is required.")
    private Integer pageSize;

    private String username;
    private Integer userStatus;
    private Set<Long> roleIds;

}
