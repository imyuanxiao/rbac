package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.imyuanxiao.rbac.exception.AccountTakeoverException;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description 自定义 UserDetailsService
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/9 14:57
 **/
@Service
public class RedisUserServiceImpl  implements UserDetailsService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetailsVO loadUserByUsername(String key) throws UsernameNotFoundException {

        Map<Object, Object> userMap = redisUtil.getUserMap(key);

        UserVO userVO = BeanUtil.mapToBean(userMap, UserVO.class, true, CopyOptions.create().setIgnoreNullValue(true));

        Set<SimpleGrantedAuthority> authorities = userVO.getPermissionIds()
                .stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UserDetailsVO(userVO, authorities);
    }

}
