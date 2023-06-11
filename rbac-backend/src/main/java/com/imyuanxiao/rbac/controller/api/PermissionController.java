package com.imyuanxiao.rbac.controller.api;


import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @description  Permission Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestController
@RequestMapping("/permission")
@Auth(id = 4000, name = "权限管理")
@Api(tags = "Permission Management Interface")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
//    @Auth(id = 1, name = "查询所有权限信息")
    @ApiOperation(value = "Get all permissions")
    public List<Permission> getPermissionList() {
        return permissionService.list();
    }

}
