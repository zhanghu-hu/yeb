package com.zh.server.mapper;

import com.zh.server.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 添加操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
