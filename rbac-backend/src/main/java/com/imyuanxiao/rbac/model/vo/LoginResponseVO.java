package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.model.vo.UserVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description login response
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
@Accessors(chain = true)
public class LoginResponseVO {

    UserVO userVO;
    String token;

}
