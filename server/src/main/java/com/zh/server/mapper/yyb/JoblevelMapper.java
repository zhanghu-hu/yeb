package com.zh.server.mapper.yyb;

import com.zh.server.entity.Joblevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface JoblevelMapper extends BaseMapper<Joblevel> {

    List<Joblevel> listALL();
}
