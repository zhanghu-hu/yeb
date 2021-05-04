package com.zh.server.server;

import com.zh.server.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据id获取菜单，id从框架中获取
     * @return
     */
    List<Menu> getMenuBuUserID();

    /**
     * 查询菜单对应的角色权限列表
     * 根据请求的url获取角色，菜单表中存有url
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
