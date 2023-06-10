package com.imyuanxiao.rbac.mapper;

import com.imyuanxiao.rbac.model.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
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

    /**
     * 根据用户id删除该用户所有角色
     * @param userId 用户id
     * @return 受影响的行数
     */
    int deleteByUserId(Serializable userId);

    /**
     * 根据用户id批量新增角色
     * @param userId 用户id
     * @param orgIds 角色id集合
     * @return 受影响的行数
     */
    int insertOrgsByUserId(@Param("userId") Long userId, @Param("orgIds") Collection<Long> orgIds);


}




