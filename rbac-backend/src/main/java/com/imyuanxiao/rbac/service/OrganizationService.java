package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【organization】的数据库操作Service
* @date  2023-06-07 20:31:35
*/
public interface OrganizationService extends IService<Organization> {
    /**
     * Get role IDs based on user ID.
     * @author imyuanxiao
     * @param id User ID
     * @return Collection of role IDs for this user.
     **/
    Set<Long> getIdsByUserId(Long id);

    /**
     * Batch add roles based on user ID.
     * @author imyuanxiao
     * @param userId User ID
     * @param orgIds Collections of organization ids
     **/
    void insertOrgsByUserId(Long userId, Collection<Long> orgIds);

    /**
     * Delete all roles based on user ID
     * @author imyuanxiao
     * @param userId User ID
     **/
    void removeByUserId(Serializable userId);
}
