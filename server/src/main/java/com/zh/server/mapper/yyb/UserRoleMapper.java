package com.zh.server.mapper.yyb;

import com.zh.server.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 添加操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);

    /**
     * 按照用户ID删除对应关系
     * @param adminId
     */
    void deleteByAdminId(@Param("adminId") Integer adminId);
}
