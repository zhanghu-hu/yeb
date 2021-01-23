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
}
