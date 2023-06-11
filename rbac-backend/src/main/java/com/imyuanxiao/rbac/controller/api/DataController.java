package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.model.vo.DataPageVO;
import com.imyuanxiao.rbac.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description Data Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestController
@RequestMapping("/data")
@Auth(id = 5000, name = "数据管理")
@Api(tags = "Data Management Interface")
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/page/{current}&{pageSize}")
//    @Auth(id = 1, name = "查询所有测试数据")
    @ApiOperation(value = "Get data based on current page")
    public IPage<DataPageVO> getPage(@PathVariable("current") int current, @PathVariable("pageSize") int pageSize) {
        // 设置分页参数
        Page<DataPageVO> page = new Page<>();
        // 设置按id升序
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("data.id");
        orderItem.setAsc(true);
        page.setCurrent(current).setSize(pageSize).addOrder(orderItem);
        return dataService.selectPage(page);
    }

}
