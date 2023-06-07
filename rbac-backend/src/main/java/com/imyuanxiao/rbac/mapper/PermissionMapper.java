package com.imyuanxiao.rbac.mapper;

import com.imyuanxiao.rbac.model.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2023-05-26 18:17:47
* @Entity com.imyuanxiao.unifiedloginsystem.model.entity.Permission
*/

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id删除该角色下权限
     * @param roleId 角色id
     * @return 受影响的行数
     */
    int deleteByRoleId(Serializable roleId);

    /**
     * 根据角色id增加角色权限
     * @param roleId 角色id
     * @param permissionIds 权限id集合
     * @return 受影响的行数
     */
    int insertPermissionsByRoleId(@Param("roleId") Long roleId, @Param("permissionIds") Collection<Long> permissionIds);

    /**
     * 根据用户id获取权限id
     * @param userId 用户id
     * @return 权限id集合
     */
    Set<Long> selectIdsByUserId(Long userId);

    /**
     * 根据角色id获取权限id
     * @param roleId 角色id
     * @return 权限id集合
     */
    Set<Long> selectIdsByRoleId(Long roleId);

    /**
     * 批量新增权限资源
     * @param permissions 资源对象集合
     * @return 受影响的行数
     */
    int insertPermissions(@Param("permissions") Collection<Permission> permissions);

    /**
     * 根据用户id获取该用户的所有权限资源对象
     * @param userId 用户id
     * @return 权限资源集合
     */
    List<Permission> selectListByUserId(Long userId);
}




