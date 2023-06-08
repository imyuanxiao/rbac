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
     * data name
     */
    @TableField(value = "data_name")
    private String dataName;

    /**
     * organization ID
     */
    @TableField(value = "org_id")
    private Long orgId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}