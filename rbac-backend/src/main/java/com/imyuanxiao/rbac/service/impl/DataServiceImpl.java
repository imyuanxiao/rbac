package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Data;
import com.imyuanxiao.rbac.service.DataService;
import com.imyuanxiao.rbac.mapper.DataMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【data】的数据库操作Service实现
* @createDate 2023-06-07 20:31:41
*/
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data>
    implements DataService{

}




