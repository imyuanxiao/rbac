package com.imyuanxiao.rbac.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/10 13:02
 **/
@Data
public class LoginHistoryListResponse {

    private Long id;

    private String username;

    private Integer type;

    private Date createdTime;

    private String ipAddress;

    private String userAgent;

}
