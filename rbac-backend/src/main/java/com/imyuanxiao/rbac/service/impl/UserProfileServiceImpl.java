package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.service.UserProfileService;
import com.imyuanxiao.rbac.mapper.UserProfileMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Service实现
* @createDate 2023-05-26 17:18:13
*/
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

    @Override
    public UserProfile getByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(UserProfile::getUserId, userId).one();
    }
}




