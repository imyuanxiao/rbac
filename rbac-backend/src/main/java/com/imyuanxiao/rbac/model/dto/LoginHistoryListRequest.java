package com.imyuanxiao.rbac.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/10 13:02
 **/
@Data
public class LoginHistoryListRequest {

    @NotNull(message = "current is required.")
    private Integer current;

    @NotNull(message = "pageSize is required.")
    private Integer pageSize;

    private String username;

}
