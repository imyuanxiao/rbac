package com.imyuanxiao.rbac.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description login response
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/6/9 13:24
 **/
@Data
@Accessors(chain = true)
public class LoginResponse {

    UserVO userVO;
    String token;

}
