package com.imyuanxiao.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.rbac.model.vo.RolePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Mapper
* @createDate 2023-05-26 18:00:54
* @Entity generator.entity.Role
*/
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色id集合
     * @param userId 用户id
     * @return 属于该用户的角色id集合
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
     * @param roleIds 角色id集合
     * @return 受影响的行数
     */
    int insertRolesByUserId(@Param("userId") Long userId, @Param("roleIds") Collection<Long> roleIds);

    /**
     * 查询用户分页信息
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页对象
     */
    IPage<RolePageVO> selectPage(Page<RolePageVO> page, @Param(Constants.WRAPPER) Wrapper<RolePageVO> wrapper);
}

