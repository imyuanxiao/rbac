package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Data;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.vo.DataPageVO;

/**
* @author Administrator
* @description 针对表【data】的数据库操作Service
* @date  2023-06-07 20:31:41
*/
public interface DataService extends IService<Data> {

    /**
     * @description Get pagination
     * @author imyuanxiao
     * @param page pagination parameters
     * @return Pagination Object
     **/
    IPage<DataPageVO> selectPage(Page<DataPageVO> page);
}
