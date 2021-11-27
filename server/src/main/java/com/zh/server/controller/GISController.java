package com.zh.server.controller;

import com.vividsolutions.jts.geom.Polygon;
import com.zh.server.request.gis.CurrentRangeRequest;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.response.gis.PointResponse;
import com.zh.server.server.GISService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.geotools.geometry.jts.GeometryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * GIS相关处理
 *
 * @author ZH
 * @date 2021-08-22
 */
@Api(tags = "GIS相关处理")
@RestController
@RequestMapping("/gis")
public class GISController {

    @Autowired
    private GISService gisService;

    @GetMapping
    @ApiOperation("获取一个指定的圆")
    public ResponseBase<String> getCircle(@RequestParam(value = "lon", defaultValue = "0") Double lon,
                                  @RequestParam(value = "lat", defaultValue = "0") Double lat,
                                  @RequestParam(value = "radius", defaultValue = "1000") Double radius) {
        radius = radius / (12 * Math.PI * 6371004) * 360; //米转度
        GeometryBuilder builder=new GeometryBuilder();
        Polygon circle = builder.circle(lon, lat, radius, 10);
        return new ResponseBase().success(circle.toString());
    }

    @GetMapping("/getAllPoint")
    @ApiOperation("获取指定范围的点位")
    public ResponseBase<PointResponse> getCurrentPoint(CurrentRangeRequest currentRangeRequest){
        List<PointResponse> pointList = gisService.getPointCollection(currentRangeRequest);
        return new ResponseBase().success(pointList);
    }
}
