package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Service实现
* @date  2023-05-26 18:17:47
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public void insertPermissions(Collection<Permission> permissions) {
        if(CollectionUtil.isEmpty(permissions)){
            return;
        }
        baseMapper.insertPermissions(permissions);
    }

    @Override
    public void deletePermissionByType(int type) {
        // 先删除所有接口类型的资源
        LambdaUpdateWrapper<Permission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Permission::getType, type);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }

}




