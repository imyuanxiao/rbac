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
 * @TableName permission
 */
@TableName(value ="permission")
@Data
@Accessors(chain = true)
public class Permission implements Serializable {
    /**
     * permission ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "permission_name")
    private String permissionName;

    /**
     * 
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 
     */
    @TableField(value = "description")
    private String description;

    /**
     * 
     */
    @TableField(value = "path")
    private String path;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}