package com.zh.server.server;

import com.zh.server.entity.MenuRole;
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
public interface MenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    String updateMenuRole(Integer rid,Integer[] mids);

    List<MenuRole> listByRid(Integer rid);
}
