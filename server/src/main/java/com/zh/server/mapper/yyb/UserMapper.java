package com.zh.server.mapper.yyb;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取所有操作员
     * @param id
     * @param keywords
     * @return
     */
    List<User> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);

    /**
     * 根据用户名获取用户实体
     * @param username
     * @return
     */
    User selectByUsername(@Param("username") String username);
}
