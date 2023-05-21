package com.zh.server.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.server.entity.Nationwide;
import com.zh.server.request.gis.CurrentRangeRequest;
import com.zh.server.response.gis.PointResponse;

import java.util.List;

public interface GISService extends IService<Nationwide> {
    /**
     * 获取指定范围的点位
     * @param currentRangeRequest
     * @return
     */
    List<PointResponse> getPointCollection(CurrentRangeRequest currentRangeRequest);

    /**
     * 将数据存入redis
     */
    void toRedis();
}
