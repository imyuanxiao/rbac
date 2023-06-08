package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName(value ="user")
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * user ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     *
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     *
     */
    @TableField(value = "user_email")
    private String userEmail;

    /**
     *
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     *
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     *
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     *
     */
    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}