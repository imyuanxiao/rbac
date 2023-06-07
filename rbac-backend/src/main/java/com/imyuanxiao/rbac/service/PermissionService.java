package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Service
* @createDate 2023-05-26 18:17:47
*/
public interface PermissionService extends IService<Permission> {

    /**
     * Get permission IDs based on user ID
     * @author imyuanxiao
     * @date 15:25 2023/5/7
     * @param userId user ID
     * @return Collections of permission IDs
     **/
    Set<Long> getIdsByUserId(Long userId);

    /**
     * Batch add permissions
     * @author imyuanxiao
     * @date 15:24 2023/5/7
     * @param permissions permissions to add
     **/
    void insertPermissions(Collection<Permission> permissions);

    /**
     * Delete permissions by type
     * @author imyuanxiao
     * @date 15:26 2023/5/7
     * @param type permission type, "0" - page-level, "1" - operation-level.
     **/
    void deletePermissionByType(int type);

    /**
     * Get permissions based on user ID
     * @author imyuanxiao
     * @date 15:27 2023/5/7
     * @param userId user ID
     * @return Collection of permissions
     **/
    List<Permission> getPermissionsByUserId(Long userId);

}
