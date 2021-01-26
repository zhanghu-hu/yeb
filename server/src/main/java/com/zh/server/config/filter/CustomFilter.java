package com.zh.server.config.filter;

import com.zh.server.entity.Menu;
import com.zh.server.entity.Role;
import com.zh.server.server.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析出请求所需角色
 * 再根据登录用户的角色，判断请求是否合法
 *
 * 每次请求都会经过这里，检查所需的角色
 * @author ZH
 * @date 2021-01-23
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation)o).getRequestUrl();
        //获取菜单，菜单改动较少访问平凡 可以放到redis里面
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
            //判断请求的url与菜单角色是否匹配
            /**路径匹配，越精准越优先
             * url列若为通配符，不要设置角色，如果设置了全角色，那随便那个用户都能访问了
             * url列设置有角色的路径不要有包含关系，否则会相互影响
             */
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        //没匹配的url默认为登录即可访问,ROLE_LOGIN是数据库没有，这里定义的
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
