package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.util.ValidationGroups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.imyuanxiao.rbac.util.CommonConst.ACTION_SUCCESSFUL;

/**
 * @description  Role Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/7 23:17
 **/
@Slf4j
@RestController
@RequestMapping("/role")
@Auth(id = 3000, name = "角色管理")
@Api(tags = "Role Management Interface")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    @Auth(id = 1, name = "新增角色")
    @ApiOperation(value = "Add role")
    public String addRole(@RequestBody @Validated(ValidationGroups.AddRole.class) RoleParam param) {
        roleService.addRole(param);
        return ACTION_SUCCESSFUL;
    }

    @DeleteMapping("/delete")
    @Auth(id = 2, name = "删除角色")
    @ApiOperation(value = "Delete role")
    public String deleteRole(@RequestBody Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        roleService.removeRolesByIds(Arrays.asList(ids));
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/update")
    @Auth(id = 3, name = "编辑角色")
    @ApiOperation(value = "Update role")
    public String updateRole(@RequestBody @Validated(ValidationGroups.UpdateResources.class) RoleParam param) {
        roleService.updateRole(param);
        return ACTION_SUCCESSFUL;
    }

    @GetMapping("/list")
    @ApiOperation(value = "Get all roles")
    public List<Role> getRoleList() {
        return roleService.list();
    }

    @GetMapping("/page/{current}&{pageSize}")
    @Auth(id = 4, name = "查看角色分页信息")
    @ApiOperation(value = "Page through role information")
    public IPage<RolePageVO> getRolePage(@PathVariable("current") int current, @PathVariable("pageSize") int pageSize) {
        // Set pagination parameters
        Page<RolePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).setSize(pageSize).addOrder(orderItem);
        return roleService.selectPage(page);
    }

}
