package com.zh.server.mapper.yyb;

import com.zh.server.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id-获取角色-获取菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(@Param("userID") Integer id);

    /**
     * 查询菜单对应的角色权限列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有目录
     * @return
     */
    List<Menu> getAllMenus();
}
