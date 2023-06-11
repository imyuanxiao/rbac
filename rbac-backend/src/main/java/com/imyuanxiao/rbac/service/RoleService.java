package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Service
* @createDate 2023-05-26 18:00:54
*/
public interface RoleService extends IService<Role> {

    /**
     * Create role
     * @author imyuanxiao
     * @date 15:14 2023/5/7
     * @param param Role-related parameters
     **/
    void addRole(RoleParam param);

    /**
     * Batch add roles based on user ID.
     * @author imyuanxiao
     * @date 15:12 2023/5/7
     * @param userId User ID
     * @param roleIds Collections of role ids
     **/
    void insertRolesByUserId(Long userId, Collection<Long> roleIds);

    /**
     * Delete all roles based on user ID
     * @author imyuanxiao
     * @date 15:12 2023/5/7
     * @param userId User ID
     **/
    void removeByUserId(Serializable userId);

    /**
     * Update permissions for this role
     * @author imyuanxiao
     * @date 15:13 2023/5/7
     * @param param Role-related parameters
     **/
    void updatePermissions(RoleParam param);

    /**
     * Get pagination information
     * @author imyuanxiao
     * @date 15:13 2023/5/7
     * @param page Pagination parameters
     * @return Pagination object
     **/
    IPage<RolePageVO> selectPage(Page<RolePageVO> page);

    /**
     * Get role IDs based on user ID.
     * @author imyuanxiao
     * @date 11:56 2023/5/7
     * @param userId User ID
     * @return Collection of role IDs for this user.
     **/
    Set<Long> getIdsByUserId(Long userId);

    /**
     * Get all information about roles based on user ID.
     * @author imyuanxiao
     * @date 15:12 2023/5/7
     * @param userId User ID
     * @return Collection of Roles for this user.
     **/
    Set<Role> getRolesByUserId(Long userId);

    boolean removeRolesByIds(Collection<?> idList);

    void updateRole(RoleParam param);
}
