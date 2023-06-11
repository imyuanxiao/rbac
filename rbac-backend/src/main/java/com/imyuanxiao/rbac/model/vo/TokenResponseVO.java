package com.imyuanxiao.rbac.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description token response
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
@Accessors(chain = true)
public class TokenResponseVO {
    private String token;

}
