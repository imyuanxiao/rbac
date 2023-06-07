package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Data;
import com.imyuanxiao.rbac.model.vo.DataPageVO;
import com.imyuanxiao.rbac.service.DataService;
import com.imyuanxiao.rbac.mapper.DataMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【data】的数据库操作Service实现
* @date  2023-06-07 20:31:41
*/
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data>
    implements DataService{

    @Override
    public IPage<DataPageVO> selectPage(Page<DataPageVO> page) {
        QueryWrapper<DataPageVO> queryWrapper = new QueryWrapper<>();
        IPage<DataPageVO> result = baseMapper.selectPage(page, queryWrapper);
        System.out.println(result.getRecords());
        return result;
    }
}




