package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.dto.UserAddRequest;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

import static com.imyuanxiao.rbac.util.CommonUtil.ACTION_SUCCESSFUL;

/**
 * @description  User Management Interface
 * @author  imyuanxiao
 * @date  2023/5/3 0:54
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

    @PostMapping("/add")
    @Auth(id = 1, name = "新增用户")
    @ApiOperation(value = "Add user")
    public String createUser(@RequestBody @Validated(UserAddRequest.CreateUser.class) UserAddRequest userAddRequest) {
        userService.createUser(userAddRequest);
        return ACTION_SUCCESSFUL;
    }

    @DeleteMapping("/delete")
    @Auth(id = 2, name = "删除用户")
    @ApiOperation(value = "Delete user")
    public String deleteUser(@RequestBody Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        userService.removeByIds(Arrays.asList(ids));
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/update")
    @Auth(id = 3, name = "编辑用户")
    @ApiOperation(value = "Update user")
    public String updateUser(@RequestBody @Validated(UserAddRequest.Update.class) UserAddRequest param) {
        userService.update(param);
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

    @GetMapping("/page/{current}&{pageSize}")
//    @Auth(id = 6, name = "分页查询用户信息")
    @ApiOperation(value = "Page through user information")
    public IPage<UserPageVO> getPage(@PathVariable("current") int current, @PathVariable("pageSize") int pageSize) {
        // 设置分页参数
        Page<UserPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).setSize(pageSize).addOrder(orderItem);
        return userService.selectPage(page);
    }

}
