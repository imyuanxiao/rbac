package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.dto.LoginRequest;
import com.imyuanxiao.rbac.model.dto.UserAddRequest;
import com.imyuanxiao.rbac.model.dto.LoginResponse;
import com.imyuanxiao.rbac.model.dto.TokenResponse;
import com.imyuanxiao.rbac.model.vo.UserPageVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Service
* @date  2023-05-26 17:15:53
*/
public interface UserService extends IService<User> {

//    User getUserByUsername(String username);

    /**
     * @description Register
     * @author imyuanxiao
     * @param registerRequest Register form parameters
     **/
    String register(LoginRequest registerRequest);

    /**
     * @description Log in
     * @author imyuanxiao
     * @param loginRequest Login form parameters
     **/
    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    /**
     * @description Add new user
     * @author imyuanxiao
     * @param param user form parameters
     **/
    void createUser(UserAddRequest param);

    /**
     * @description Update user information
     * @author imyuanxiao
     * @param param user form parameters
     **/
    void update(UserAddRequest param);

    /**
     * @description Get pagination information
     * @author imyuanxiao
     * @param page pagination parameters
     **/
    IPage<UserPageVO> selectPage(Page<UserPageVO> page);

    /**
     * @description Get UserVO by using user in context
     * @author imyuanxiao
     **/
    Set<Long> myPermission();

    TokenResponse updateToken();

    String sendCaptcha(String phone);

    void logout(HttpServletRequest request);

    IPage<UserPageVO> selectPageByConditions(Page<UserPageVO> page, QueryWrapper<UserPageVO> queryWrapper);
}
