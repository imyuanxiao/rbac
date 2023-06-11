package com.imyuanxiao.rbac.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description  UserDetails object used for spring security
 * @author  imyuanxiao
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetailsVO extends org.springframework.security.core.userdetails.User {

    private UserVO user;

    public UserDetailsVO(UserVO user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), "", authorities);
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

}
