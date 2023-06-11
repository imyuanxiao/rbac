package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Service实现
* @createDate 2023-05-26 18:00:54
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public Set<Role> getRolesByUserId(Long userId) {
        Set<Long> roleIds = baseMapper.selectIdsByUserId(userId);
        Set<Role> roleList = new HashSet<>();
        for(Long roleId: roleIds){
            roleList.add(this.getById(roleId));
        }
        return roleList;
    }

    @Override
    public void removeByUserId(Serializable userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    public void insertRolesByUserId(Long userId, Collection<Long> roleIds) {
        baseMapper.insertRolesByUserId(userId, roleIds);
    }

    @Override
    public IPage<RolePageVO> selectPage(Page<RolePageVO> page) {
        QueryWrapper<RolePageVO> queryWrapper = new QueryWrapper<>();
        // 获取分页列表
        IPage<RolePageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // 再查询各角色的权限
        for (RolePageVO vo : pages.getRecords()) {
            vo.setPermissionIds(permissionMapper.selectIdsByRoleId(vo.getId()));
        }
        return pages;
    }

    @Override
    public void updatePermissions(RoleParam param) {
        // 先删除原有角色对应的权限数据
        permissionMapper.deleteByRoleId(param.getId());
        // 如果新的权限ID为空就代表删除所有权限，不用后面新增流程了
        if (CollUtil.isEmpty(param.getPermissionIds())) {
            return;
        }
        // 新增权限ID
        permissionMapper.insertPermissionsByRoleId(param.getId(), param.getPermissionIds());
    }

    @Override
    public void addRole(RoleParam param) {
        if (lambdaQuery().eq(Role::getRoleName, param.getRoleName()).one() != null) {
            throw new ApiException(ResultCode.FAILED, "角色名重复");
        }
        // 新增角色
        Role newRole = BeanUtil.copyProperties(param, Role.class);
        // 保存对象时，会将新增的id赋予该对象
        save(newRole);
        if (CollUtil.isEmpty(param.getPermissionIds())) {
            return;
        }
        // 再新增权限数据
        permissionMapper.insertPermissionsByRoleId(newRole.getId(), param.getPermissionIds());
    }

    public boolean removeRolesByIds(Collection<?> idList) {
        if (CollUtil.isEmpty(idList)) {
            return false;
        }
        // 删除角色下所属的权限
        for (Object roleId : idList) {
            permissionMapper.deleteByRoleId((Long)roleId);
        }
        // 删除角色
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public void updateRole(RoleParam param) {
        // 提取Role信息
        Role updateRole = BeanUtil.copyProperties(param, Role.class);
        // 更新Role基本资料
        lambdaUpdate().eq(Role::getId, updateRole.getId()).update(updateRole);
        // 更新Role对应权限
        this.updatePermissions(param);
    }

}




