package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;


@TableName(value ="user_profile")
@Data
@Accessors(chain = true)
public class UserProfile implements Serializable {
    /**
     * profile ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * nick name
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * avatar
     */
    @TableField(value = "avatar")
    private String avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}