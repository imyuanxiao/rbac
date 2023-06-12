package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.service.UserProfileService;
import com.imyuanxiao.rbac.mapper.UserProfileMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Service实现
* @date  2023-05-26 17:18:13
*/
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

    @Override
    public UserProfile getByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(UserProfile::getUserId, userId).one();
    }

    public boolean updateByUserId(UserProfile userProfile) {

        return lambdaUpdate().eq(ObjUtil.isNotNull(userProfile.getUserId()), UserProfile::getUserId, userProfile.getUserId()).update(userProfile);

    }

}




