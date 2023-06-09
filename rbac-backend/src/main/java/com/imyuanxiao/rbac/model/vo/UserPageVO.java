package com.imyuanxiao.rbac.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @description  User pagination object.
 * @author  imyuanxiao
 **/
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private int userStatus;
    private String userPhone;
    private String userEmail;

    private Set<Long> roleIds;
    private Set<Long> orgIds;
}
