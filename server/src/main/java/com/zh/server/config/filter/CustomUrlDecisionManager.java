package com.zh.server.config.filter;

import com.zh.server.entity.Role;
import com.zh.server.entity.User;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制
 * 判断用户角色
 *
 * 每次查询（白名单的不会过来）都会经过这里，检查登录用户是否有所需的角色
 * @author ZH
 * @date 2021-01-23
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            //访问当前url所需的角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否为登录即可访问的角色,ROLE_LOGIN
            if ("ROLE_LOGIN".equals(needRole)){
                //判断登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    try {
                        throw new RuntimeException("用户未登录，请先登录");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    return;
                }
            }
            //判断角色是否为url所需角色
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(needRole)){
//                    return;
//                }
//            }
            Object o1=authentication.getPrincipal();
            if (o1 instanceof User) {
                User principal = (User) authentication.getPrincipal();
                for (Role authority : principal.getRoles()) {
                    if (authority.getName().equals(needRole)) {
                        return;
                    }
                }
            }
        }
        //401
        throw new AccessDeniedException("CustomUrlDecisionManager : 权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
