package com.imyuanxiao.rbac.util;

import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @description  获取上下文对象中保存的用户信息
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
public class SecurityContextUtil {

    /**
     * Get user ID from spring security context
     * @author imyuanxiao
     * @return User ID
     **/
    public static Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVO userDetails = (UserDetailsVO)authentication.getPrincipal();
        return userDetails.getId();
    }

    /**
     * Get user object from spring security context
     * @author imyuanxiao
     * @return User Object
     **/
    public static UserVO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVO userDetails = (UserDetailsVO)authentication.getPrincipal();
        return userDetails.getUser();
    }

    public static UserDetailsVO getCurrentUserDetailsVO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsVO)authentication.getPrincipal();
    }

}
