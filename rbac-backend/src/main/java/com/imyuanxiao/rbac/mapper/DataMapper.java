package com.imyuanxiao.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Data;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.rbac.model.vo.DataPageVO;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【data】的数据库操作Mapper
* @date  2023-06-07 20:31:41
*/
public interface DataMapper extends BaseMapper<Data> {
    /**
     * 查询数据分页信息
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页对象
     */
    IPage<DataPageVO> selectPage(Page<DataPageVO> page, @Param(Constants.WRAPPER) Wrapper<DataPageVO> wrapper);
}




