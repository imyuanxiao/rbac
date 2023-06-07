package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName user_auth
 */
@TableName(value ="user_auth")
@Data
@Accessors(chain = true)
public class UserAuth implements Serializable {
    /**
     * auth ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 
     */
    @TableField(value = "login_type")
    private Integer loginType;

    /**
     * 
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 
     */
    @TableField(value = "access_token")
    private String accessToken;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}