package com.zh.server.server.impl;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.server.entity.Nationwide;
import com.zh.server.mapper.gis.GISMapper;
import com.zh.server.request.gis.CurrentRangeRequest;
import com.zh.server.response.gis.PointResponse;
import com.zh.server.server.GISService;
import com.zh.server.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class GISServiceImpl extends ServiceImpl<GISMapper, Nationwide> implements GISService {

    @Autowired
    private GISMapper gisMapper;

    @Value("${zoom.one}")
    private String oneLevel;
    @Value("${zoom.two}")
    private String twoLevel;
    @Value("${zoom.three}")
    private String threeLevel;
    @Value("${zoom.four}")
    private String fourLevel;

    @Override
    public List<PointResponse> getPointCollection(CurrentRangeRequest currentRangeRequest) {
        Integer zoom = currentRangeRequest.getZoom();
        List<PointResponse> result = new ArrayList<>();
        List<Integer> oneLevelList = StringUtil.StringToIntegers(oneLevel, ",");
        List<Integer> twoLevelList = StringUtil.StringToIntegers(twoLevel, ",");
        List<Integer> threeLevelList = StringUtil.StringToIntegers(threeLevel, ",");
        List<Integer> fourLevelList = StringUtil.StringToIntegers(fourLevel, ",");
        if (zoom <= fourLevelList.get(1)) { //返回聚合数据
            Integer presion;
            if (oneLevelList.get(0) <= zoom && oneLevelList.get(1) >= zoom) {
                presion = oneLevelList.get(2);
            } else if (twoLevelList.get(0) <= zoom && twoLevelList.get(1) >= zoom) {
                presion = twoLevelList.get(2);
            } else if (threeLevelList.get(0) <= zoom && threeLevelList.get(1) >= zoom) {
                presion = threeLevelList.get(2);
            } else {
                presion = fourLevelList.get(2);
            }
            GeoHash maxGeoHash = GeoHash.withCharacterPrecision(currentRangeRequest.getMaxY(), currentRangeRequest.getMaxX(), presion);
            WGS84Point maxPoint = maxGeoHash.getBoundingBoxCenter();
            GeoHash minGeoHash = GeoHash.withCharacterPrecision(currentRangeRequest.getMinY(), currentRangeRequest.getMinX(), presion);
            WGS84Point minPoint = minGeoHash.getBoundingBoxCenter();
            while (!minGeoHash.equals(maxGeoHash)) {
                WGS84Point startPoint = minGeoHash.getBoundingBoxCenter();
                if (startPoint.getLatitude() < minPoint.getLatitude() && startPoint.getLongitude() < minPoint.getLongitude() &&
                        startPoint.getLatitude() > maxPoint.getLatitude() && startPoint.getLatitude() > maxPoint.getLatitude()) {
                    minGeoHash = minGeoHash.next();
                    continue;
                }
                if (getPointData(minGeoHash) != null)
                    result.add(getPointData(minGeoHash));
                minGeoHash = minGeoHash.next();
            }
            //最后一个网格
            if (getPointData(minGeoHash) != null)
                result.add(getPointData(minGeoHash));
        } else {//返回非聚合的数据，即具体点的数据集合
            char[] geoHashCommonChar = new char[]{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1'};
            char[] maxGeoChar = GeoHash.withCharacterPrecision(currentRangeRequest.getMaxY(), currentRangeRequest.getMaxX(), 10).toBase32()
                    .toCharArray();
            char[] minGeoChar = GeoHash.withCharacterPrecision(currentRangeRequest.getMinY(), currentRangeRequest.getMinX(), 10).toBase32()
                    .toCharArray();
            for (int i = 0; i < 10; i++) {
                if (minGeoChar[i] == maxGeoChar[i]) {
                    geoHashCommonChar[i] = minGeoChar[i];
                } else {
                    break;
                }
            }
            String geoHashCommonStr = new String(geoHashCommonChar);
            Integer index = geoHashCommonStr.indexOf('1');
            geoHashCommonStr = geoHashCommonStr.substring(0, index);
            result = gisMapper.getPointData(geoHashCommonStr);
        }
        return result;
    }

    private PointResponse getPointData(GeoHash minGeoHash) {
        PointResponse pointData = gisMapper.getClusterPointData(minGeoHash.toBase32());
        if (pointData.getCount() == 1) {
            pointData = gisMapper.getPointData(minGeoHash.toBase32()).get(0);
        } else if (pointData.getCount() == 0) {
            return null;
        }
        return pointData;
    }
}
