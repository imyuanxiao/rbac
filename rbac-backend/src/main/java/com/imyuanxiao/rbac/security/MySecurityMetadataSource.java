package com.imyuanxiao.rbac.security;

import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @description  鉴权规则源SecurityMetadataSource,获取当前请求的鉴权规则
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@Component("mySecurityMetadataSource")
public class MySecurityMetadataSource implements SecurityMetadataSource {

    @Autowired
    private PermissionService permissionService;
    /**
     * 当前系统所有url资源
     */
    @Getter
    private static final Set<Permission> PERMISSIONS = new HashSet<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        log.info("---MySecurityMetadataSource---");
        // 该对象是Spring Security帮我们封装好的，可以通过该对象获取request等信息
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getRequest();
        // 遍历所有权限资源，以和当前请求所需的权限进行匹配
        for (Permission permission : PERMISSIONS) {
            // 因为我们url资源是这种格式：GET:/API/user/test/{id}，冒号前面是请求方法，冒号后面是请求路径，所以要字符串拆分
            String[] split = permission.getPath().split(":");
            // 因为/API/user/test/{id}这种路径参数不能直接equals来判断请求路径是否匹配，所以需要用Ant类来匹配
            AntPathRequestMatcher ant = new AntPathRequestMatcher(split[1]);
            // 如果请求方法和请求路径都匹配上了，则代表找到了这个请求所需的权限资源
            if (request.getMethod().equals(split[0]) && ant.matches(request)) {
                // 将我们权限资源id返回
                return Collections.singletonList(new SecurityConfig(permission.getId().toString()));
            }
        }
        // 走到这里就代表该请求无需授权即可访问，返回空
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @PostConstruct
    public void init() {
        log.info("Initializing permissions...");
        loadPermissions();
    }

    private void loadPermissions() {
        List<Permission> permissions = permissionService.list();
        if (permissions != null && !permissions.isEmpty()) {
            List<Permission> filteredPermissions = permissions.stream()
                    .filter(permission -> permission.getType() == 1).toList();
            PERMISSIONS.clear();
            PERMISSIONS.addAll(filteredPermissions);
            log.info("Loaded {} permissions.", filteredPermissions.size());
        } else {
            log.warn("No permissions found.");
        }
    }

}