package com.imyuanxiao.rbac.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description token response
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/9 15:54
 **/
@Data
@Accessors(chain = true)
public class TokenResponse {
    private String token;

}
