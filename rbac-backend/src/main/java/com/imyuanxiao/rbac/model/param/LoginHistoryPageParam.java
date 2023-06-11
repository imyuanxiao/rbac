package com.imyuanxiao.rbac.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class LoginHistoryPageParam {

    @NotNull(message = "current is required.")
    private Integer current;

    @NotNull(message = "pageSize is required.")
    private Integer pageSize;

    private String username;

}
