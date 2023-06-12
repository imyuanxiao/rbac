package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.param.*;
import com.imyuanxiao.rbac.model.vo.LoginHistoryPageVO;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.UserProfileService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import com.imyuanxiao.rbac.util.ValidationGroups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

import static com.imyuanxiao.rbac.util.CommonConst.ACTION_SUCCESSFUL;

/**
 * @description  User Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@Auth(id = 1000, name = "账户管理")
@Api(tags = "User Management Interface")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/add")
    @Auth(id = 1, name = "新增用户")
    @ApiOperation(value = "Add user")
    public String createUser(@RequestBody @Validated(ValidationGroups.AddUser.class) UserParam param) {
        userService.createUser(param);
        return ACTION_SUCCESSFUL;
    }

    @DeleteMapping("/delete")
    @Auth(id = 2, name = "删除用户")
    @ApiOperation(value = "Delete user")
    public String deleteUser(@RequestBody Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        userService.removeUserByIds(Arrays.asList(ids));
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/update")
    @Auth(id = 3, name = "编辑用户")
    @ApiOperation(value = "Update user")
    public String updateUser(@RequestBody @Validated(ValidationGroups.UpdateUser.class) UserParam param) {
        userService.update(param);
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/profile")
    @ApiOperation(value = "Update user profile")
    public String updateUserProfile(@RequestBody UserProfileParam param) {
        userService.updateUserProfile(param);
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/profile/password")
    @ApiOperation(value = "Update user password")
    public String updateUserPassword(@RequestBody UserPasswordParam param) {
        userService.updateUserPassword(param);
        return ACTION_SUCCESSFUL;
    }

    @GetMapping("/get/{id}")
    @Auth(id = 4, name = "通过id获取用户信息")
    @ApiOperation(value = "Get user info based on user ID")
    public User getUserById(@ApiParam(value = "User ID", required = true)
                            @PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/permissions/{id}")
    @Auth(id = 5, name = "通过id获取用户权限")
    @ApiOperation(value = "Get permissions based on user ID")
    public Set<Long> getPermissionsByUserId(@PathVariable("id") Long userId) {
        return permissionService.getIdsByUserId(userId);
    }

    @PostMapping("/page")
    @ApiOperation(value = "Page through user information by conditions")
    @Auth(id = 6, name = "查看用户分页信息")
    public IPage<UserPageVO> getUserPageByConditions(@RequestBody UserPageParam param) {

        // 设置分页参数
        Page<UserPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);

        // 构建查询条件
        Long myId = SecurityContextUtil.getCurrentUserId();

        QueryWrapper<UserPageVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(param.getUsername()), "username", param.getUsername())
                .eq(param.getUserStatus() != null, "user_status", param.getUserStatus())
                .ne("id", myId);

        Set<Long> roleIds = param.getRoleIds();
        if (!CollUtil.isEmpty(roleIds)) {
            String roleIdList = CollUtil.join(roleIds, ",");
            String subQuery = "SELECT user_id FROM user_role WHERE role_id IN (" + roleIdList + ") GROUP BY user_id HAVING COUNT(DISTINCT role_id) = " + roleIds.size();
            queryWrapper.apply("id IN (SELECT id FROM user WHERE id IN (" + subQuery + ") AND (SELECT COUNT(*) FROM user_role WHERE user_id = user.id AND role_id IN (" + roleIdList + ")) = " + roleIds.size() + ")");
        }

        return userService.selectPageByConditions(page, queryWrapper);
    }


    @GetMapping("/page/{current}&{pageSize}")
    @ApiOperation(value = "Page through user information")
    public IPage<UserPageVO> getUserPage(@PathVariable("current") int current, @PathVariable("pageSize") int pageSize) {
        // 设置分页参数
        Page<UserPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).setSize(pageSize).addOrder(orderItem);
        return userService.selectPage(page);
    }

    @PostMapping("/loginHistory")
    @ApiOperation(value = "Page through login history by conditions")
    @Auth(id = 7, name = "查看登录历史分页信息")
    public IPage<LoginHistoryPageVO> getLoginHistoryByConditions(@RequestBody LoginHistoryPageParam param) {
        return userService.getLoginHistoryByConditions(param);
    }



}
