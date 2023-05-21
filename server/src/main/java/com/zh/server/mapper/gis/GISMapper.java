package com.zh.server.mapper.gis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.server.entity.Nationwide;
import com.zh.server.response.gis.PointResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-08-15
 */
@Mapper
public interface GISMapper extends BaseMapper<Nationwide> {

    /**
     * 获取聚合点位
     * @param base GeoHash编码
     * @return
     */
    PointResponse getClusterPointData(String base);

    /**
     * 获取具体点位
     * @param base GeoHash编码
     * @return
     */
    List<PointResponse> getPointData(String base);
}
