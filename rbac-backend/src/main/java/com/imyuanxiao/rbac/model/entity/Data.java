package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName data
 */
@TableName(value ="data")
@lombok.Data
public class Data implements Serializable {
    /**
     * data ID, unique
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * data name, unique
     */
    @TableField(value = "name")
    private String name;

    /**
     * organization ID
     */
    @TableField(value = "organization_id")
    private Long organizationId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}