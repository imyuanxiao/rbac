package com.imyuanxiao.rbac.mapper;

import com.imyuanxiao.rbac.model.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
* @author Administrator
* @description 针对表【organization】的数据库操作Mapper
* @date  2023-06-07 20:31:35
*/
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 根据用户id查询org id集合
     * @param userId 用户id
     * @return 属于该用户的org id集合
     */
    Set<Long> selectIdsByUserId(Long userId);

}




