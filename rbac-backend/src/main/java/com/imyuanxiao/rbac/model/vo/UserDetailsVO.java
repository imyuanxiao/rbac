package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.model.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description  UserDetails object used for spring security
 * @author  imyuanxiao
 * @date  2023/5/4 22:44
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetailsVO extends org.springframework.security.core.userdetails.User {

    private User user;

    private String token;

    public UserDetailsVO(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getUserPassword(), authorities);
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

}
