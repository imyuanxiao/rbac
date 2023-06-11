package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class LoginHistoryPageVO {

    private Long id;

    private String username;

    private Integer type;

    private Date createdTime;

    private String ipAddress;

    private String userAgent;

}
