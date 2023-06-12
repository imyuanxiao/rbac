package com.imyuanxiao.rbac.model.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.imyuanxiao.rbac.util.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class UserProfileParam{

    private String userPhone;

    private String userEmail;

    @NotBlank(message = "Nickname is required.")
    private String nickName;

//    private String avatar;

}