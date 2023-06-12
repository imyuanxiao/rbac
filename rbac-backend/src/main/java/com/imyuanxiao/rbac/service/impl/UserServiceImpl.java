package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.param.*;
import com.imyuanxiao.rbac.model.entity.UserLoginHistory;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.model.vo.*;
import com.imyuanxiao.rbac.security.JwtManager;
import com.imyuanxiao.rbac.service.*;
import com.imyuanxiao.rbac.util.CommonConst;
import com.imyuanxiao.rbac.util.RedisUtil;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Service实现
* @date  2023-05-26 17:15:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserLoginHistoryService loginHistoryService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String sendCaptcha(String phone) {
        String code = RandomUtil.randomNumbers(4);
        // Save email and code in redis
        redisUtil.saveCode(phone, code);
        // TODO send code to email
        return "Verification code has been sent: " + code;
    }

    @Override
    public String register(LoginRequestParam param) {

        // 通过注册类型判断应该采用哪种注册方式
        String type = param.getType();

        // 1. 手机号注册
        if (CommonConst.MOBILE.equals(type) && registerByPhone(param.getMobile(), param.getCaptcha())) {
            return "Register successfully";
        }
        // 2. 密码注册
        if (StrUtil.isBlank(param.getPassword())) {
            throw new ApiException(ResultCode.PARAMS_ERROR, "密码格式错误！");
        }
        // 3. 普通用户名密码注册
        User user = new User()
                .setUsername(param.getUsername())
                .setUserStatus(0)
                .setUserPassword(passwordEncoder.encode(param.getPassword()));
        try {
            this.save(user);
            // Add default user role - 2L user
            roleService.insertRolesByUserId(user.getId(), List.of(2L));
            return "Register successfully";
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "Account is already in use.");
        }
    }

    public boolean registerByPhone(String phone, String captcha) {
        // Get captcha from redis
        redisUtil.getCaptcha(phone, captcha);
        User user = new User()
                .setUsername(phone)
                .setUserPhone(phone)
                .setUserStatus(0)
                // 默认密码为手机号
                .setUserPassword(passwordEncoder.encode(phone));
        try {
            this.save(user);
            // Add default user role - 2L user
            roleService.insertRolesByUserId(user.getId(), List.of(2L));
            redisUtil.removeCaptcha(phone);
            return true;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "Phone is already in use.");
        }
    }

    @Override
    public LoginResponseVO login(LoginRequestParam param, HttpServletRequest request) {

        User userResult = null;
        // 如果登录类型为手机号验证码
        if (CommonConst.MOBILE.equals(param.getType())) {
            String phone = param.getMobile();
            // Get user by phone from database
            userResult = this.lambdaQuery()
                    .eq(StrUtil.isNotBlank(phone), User::getUserPhone, phone)
                    .one();
            // 用户不存在，且则自动注册新账号
            if (userResult == null) {
                registerByPhone(phone, param.getCaptcha());
                // 注册成功，重新查询用户信息
                userResult = this.lambdaQuery()
                        .eq(StrUtil.isNotBlank(phone), User::getUserPhone, phone)
                        .one();
            } else {
                // 用户存在，表示正在使用验证码登录
                // 从redis验证手机号和验证码
                redisUtil.getCaptcha(phone, param.getCaptcha());
                // 移除手机号和验证码，登录成功
                redisUtil.removeCaptcha(phone);
                // 下一步，验证账户有效性，需要返回token
            }
        } else {
            String username = param.getUsername();
            userResult = this.lambdaQuery()
                    .eq(StrUtil.isNotBlank(username), User::getUsername, username)
                    .one();
            if (userResult == null || !passwordEncoder.matches(param.getPassword(), userResult.getUserPassword())) {
                throw new ApiException(ResultCode.VALIDATE_FAILED, "Username or password is incorrect！");
            }
        }

        // If state is abnormal
        if (userResult.getUserStatus() != CommonConst.USER_STATUS_NORMAL) {
            throw new ApiException(userResult.getUserStatus() == CommonConst.USER_STATUS_DISABLED ?
                    ResultCode.ACCOUNT_STATE_DISABLED :
                    ResultCode.ACCOUNT_STATE_DELETED);
        }

        // Save login info
        loginHistoryService.save(new UserLoginHistory()
                .setUserId(userResult.getId())
                .setType(CommonConst.LOG_IN)
                .setUserAgent(request.getHeader("User-Agent"))
                .setIpAddress(request.getRemoteAddr()));

        // Put user basic info, profile, token, permissions in UserVO object
        UserVO userVO = getUserVO(userResult);

        // save UserMap to redis
        // Manually handle or use util to convert id 'long' to 'string'.
        Map<String, Object> userMap = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue != null ? fieldValue.toString() : null));
        // Generate token
        String token = JwtManager.generate(userResult.getUsername(), userResult.getId());
        // Add token to userMap
        userMap.put("token", token);
        // Save user info and token in redis
        redisUtil.saveUserMap(userMap);

        // return loginResponse
        return new LoginResponseVO().setUserVO(userVO).setToken(token);
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        // Copy basic info
        BeanUtil.copyProperties(user, userVO);
        // Copy user profile
        UserProfile userProfile = userProfileService.getByUserId(user.getId());
        // Initialize user profile if new user
        if (userProfile == null) {
            userProfile = new UserProfile()
                    .setNickName(user.getUsername())
                    .setAvatar("https://i.328888.xyz/2023/05/15/VZpOIx.png");
            userProfile.setUserId(user.getId());
            userProfileService.save(userProfile);
        }
        // 讲userProfile数据拷贝到userVO，但是忽视id
        BeanUtil.copyProperties(userProfile, userVO, "id", "userID");
        // Set roleIds and permissionIds
        userVO.setRoleIds(roleService.getIdsByUserId(user.getId()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
    }


    @Override
    public void logout(HttpServletRequest request) {
        loginHistoryService.save(new UserLoginHistory()
                .setUserId(SecurityContextUtil.getCurrentUserDetailsVO().getUser().getId())
                .setType(CommonConst.LOG_OUT)
                .setUserAgent(request.getHeader("User-Agent"))
                .setIpAddress(request.getRemoteAddr()));
        redisUtil.removeUserMap();
    }


    @Override
    public Set<Long> myPermission() {
        Long currentUserId = SecurityContextUtil.getCurrentUserId();
        return permissionService.getIdsByUserId(currentUserId);
    }

    @Override
    public TokenResponseVO updateToken() {
        String newToken = redisUtil.refreshToken();
        return new TokenResponseVO().setToken(newToken);
    }

    @Override
    public void createUser(UserParam param) {
        checkUsername(param.getUsername());
        User user = new User();
        user.setUsername(param.getUsername()).setUserPassword(passwordEncoder.encode(param.getUsername()));
        save(user);
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // Add info in table [user-role]
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    private void checkUsername(String username) {
        if (lambdaQuery().eq(User::getUsername, username).one() != null) {
            throw new ApiException(ResultCode.FAILED, "Account already exists.");
        }
    }

    public boolean removeUserByIds(Collection<?> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return false;
        }

        for (Object userId : idList) {
            // Delete info from table [user-role]
            roleService.removeByUserId((Long) userId);

            // Delete user history from user_login_history
            loginHistoryService.removeByUserId((Long) userId);

            // Delete user from redis
            String username = lambdaQuery().eq(User::getId, userId).one().getUsername();
            redisUtil.removeUserMapByUsername(username);

        }


        // Delete user
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public void updateUserProfile(UserProfileParam param) {

        Long currentUserId = SecurityContextUtil.getCurrentUserId();

        // 更新手机号邮箱
        lambdaUpdate()
                .set(StrUtil.isNotBlank(param.getUserPhone()), User::getUserPhone, param.getUserPhone())
                .set(StrUtil.isNotBlank(param.getUserEmail()), User::getUserEmail, param.getUserEmail())
                .eq(User::getId, currentUserId).update();

        // 更新其他资料
        UserProfile userProfile = BeanUtil.copyProperties(param, UserProfile.class);
        userProfile.setUserId(currentUserId);
        userProfileService.updateByUserId(userProfile);

        // 更新redis内用户资料
        User userResult = this.lambdaQuery()
                .eq(User::getId, currentUserId)
                .one();
        // Put user basic info, profile, token, permissions in UserVO object
        UserVO userVO = getUserVO(userResult);
        // save UserMap to redis
        // Manually handle or use util to convert id 'long' to 'string'.
        Map<String, Object> userMap = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue != null ? fieldValue.toString() : null));
        // Save user info and token in redis
        redisUtil.saveUserMap(userMap);

    }

    @Override
    public void updateUserPassword(UserPasswordParam param) {

        Long currentUserId = SecurityContextUtil.getCurrentUserId();

        User userResult = lambdaQuery()
                .eq(User::getId, currentUserId).one();

        if (userResult == null || !passwordEncoder.matches(param.getOldPassword(), userResult.getUserPassword())) {
            throw new ApiException(ResultCode.VALIDATE_FAILED, "旧密码输入错误！");
        }

        this.lambdaUpdate()
                .set(User::getUserPassword, passwordEncoder.encode(param.getNewPassword()))
                .eq(User::getId, currentUserId).update();

    }

    @Override
    public void update(UserParam param) {

        // 检查用户名是否存在（排除当前ID）
        if (lambdaQuery().ne(User::getId, param.getId()).eq(User::getUsername, param.getUsername()).one() != null) {
            throw new ApiException(ResultCode.FAILED, "Account already exists.");
        }

        // 提取User信息
        User updateUser = BeanUtil.copyProperties(param, User.class);

        // 更新基本信息（含手机号和邮箱）
        lambdaUpdate().eq(User::getId, updateUser.getId()).update(updateUser);

        // 更新角色
        updateRoles(param);

        // 更新组织
        updateOrgs(param);
    }

    private void updateOrgs(UserParam param) {
        // Delete the original user role
        organizationService.removeByUserId(param.getId());
        // If roleIds is empty, delete all roles for this user
        if (CollectionUtil.isEmpty(param.getOrgIds())) {
            return;
        }
        // If orgIds not empty, add new roles for this user
        organizationService.insertOrgsByUserId(param.getId(), param.getOrgIds());
    }

    private void updateRoles(UserParam param) {
        // Delete the original user role
        roleService.removeByUserId(param.getId());
        // If roleIds is empty, delete all roles for this user
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // If roleIds not empty, add new roles for this user
        roleService.insertRolesByUserId(param.getId(), param.getRoleIds());
    }

    @Override
    public IPage<UserPageVO> selectPageByConditions(Page<UserPageVO> page, QueryWrapper<UserPageVO> queryWrapper) {
        IPage<UserPageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // Get roles and organizations for all users
        for (UserPageVO vo : pages.getRecords()) {
            vo.setRoleIds(roleService.getIdsByUserId(vo.getId()));
            vo.setOrgIds(organizationService.getIdsByUserId(vo.getId()));
        }
        return pages;
    }

    @Override
    public IPage<UserPageVO> selectPage(Page<UserPageVO> page) {
        QueryWrapper<UserPageVO> queryWrapper = new QueryWrapper<>();
        // Don't show super admin (id:1) and current user
        Long myId = SecurityContextUtil.getCurrentUserId();
        queryWrapper.ne("id", myId).ne("id", 1);
        // Get page info
        IPage<UserPageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // Get roles and organizations for all users
        for (UserPageVO vo : pages.getRecords()) {
            vo.setRoleIds(roleService.getIdsByUserId(vo.getId()));
            vo.setOrgIds(organizationService.getIdsByUserId(vo.getId()));
        }
        return pages;
    }

    @Override
    public IPage<LoginHistoryPageVO> getLoginHistoryByConditions(LoginHistoryPageParam param) {

        // 设置分页参数
        Page<LoginHistoryPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("created_time");
        orderItem.setAsc(false);
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);

        return baseMapper.selectLoginHistory(page, param.getUsername());
    }


}


