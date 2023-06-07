package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName user_login_history
 */
@TableName(value ="user_login_history")
@Data
@Accessors(chain = true)
public class UserLoginHistory implements Serializable {
    /**
     * user login history ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * login time
     */
    @TableField(value = "login_time")
    private Date loginTime;

    /**
     * logout time
     */
    @TableField(value = "logout_time")
    private Date logoutTime;

    /**
     * ip address
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    @TableField(value = "user_agent")
    private String userAgent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}