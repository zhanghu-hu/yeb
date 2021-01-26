package com.zh.server.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.server.entity.Menu;
import com.zh.server.entity.User;
import com.zh.server.mapper.MenuMapper;
import com.zh.server.server.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuBuUserID() {
        List<Menu> menuList=null;

        //登录(login)的时候放进去的
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //此处省略redis缓存步骤(因为菜单改动少，几乎固定)

        menuList=menuMapper.getMenusByAdminId(user.getTId());

        return menuList;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
