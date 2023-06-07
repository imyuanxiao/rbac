package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.UserAuth;
import com.imyuanxiao.rbac.service.UserAuthService;
import com.imyuanxiao.rbac.mapper.UserAuthMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_auth】的数据库操作Service实现
* @createDate 2023-05-26 17:16:51
*/
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth>
    implements UserAuthService{

}




