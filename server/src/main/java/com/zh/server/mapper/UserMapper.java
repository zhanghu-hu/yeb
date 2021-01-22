package com.zh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.server.entity.User;
import org.apache.ibatis.annotations.Mapper;

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

}
