package com.imyuanxiao.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Mapper
* @date  2023-05-26 17:15:53
*/
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户分页信息
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页对象
     */
    IPage<UserPageVO> selectPage(Page<UserPageVO> page, @Param(Constants.WRAPPER) Wrapper<UserPageVO> wrapper);
}

