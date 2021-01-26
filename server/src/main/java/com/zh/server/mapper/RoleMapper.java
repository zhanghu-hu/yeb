package com.zh.server.mapper;

import com.zh.server.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色列表
     * 与请求菜单的角色做对比，看看用户请求的合法性
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(@Param("adminId") Integer adminId);
}
