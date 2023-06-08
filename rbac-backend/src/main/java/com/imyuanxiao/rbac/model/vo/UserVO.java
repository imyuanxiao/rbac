package com.imyuanxiao.rbac.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @ClassName UserVO
 * @Description User Value Object
 * @Author imyuanxiao
 * @Date 2023/5/4 15:05
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

//    private String userEmail;
//
//    private String userPhone;

    private String avatar;

    private String nickName;

    private Set<Long> roleIds;

    private Set<Long> permissionIds;

    private String token;

}
