package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Organization;
import com.imyuanxiao.rbac.service.OrganizationService;
import com.imyuanxiao.rbac.mapper.OrganizationMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
* @author Administrator
* @description 针对表【organization】的数据库操作Service实现
* @date  2023-06-07 20:31:35
*/
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService{
    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }
}




