package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Service
* @createDate 2023-05-26 17:18:13
*/
public interface UserProfileService extends IService<UserProfile> {

    UserProfile getByUserId(Long userId);
}
