package com.imyuanxiao.rbac.util;

import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName SecurityContextUtil
 * @Description TODO
 * @Author imyuanxiao
 * @Date 2023/5/7 12:44
 * @Version 1.0
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
    public static User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVO userDetails = (UserDetailsVO)authentication.getPrincipal();
        return userDetails.getUser();
    }

    public static UserDetailsVO getCurrentUserDetailsVO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsVO)authentication.getPrincipal();
    }

}
