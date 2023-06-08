package com.imyuanxiao.rbac.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DataPageVO {
    /**
     * data ID, unique
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * data name, unique
     */
    @TableField(value = "data_name")
    private String dataName;

    /**
     * organization ID
     */
    @TableField(value = "org_id")
    private Long orgId;

}