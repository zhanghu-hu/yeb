package com.zh.server.utils;

import com.zh.server.response.gis.PointResponse;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class FeatureUtil {

    private static SimpleFeatureType featureType;

    private SimpleFeatureType getFeatureType() {
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("全国点位图层");
        typeBuilder.setSRS("EPSG:4326");

//        typeBuilder.add("id", Integer.class);
        typeBuilder.add("province", String.class);
        typeBuilder.add("city", String.class);
        typeBuilder.add("cityCode", String.class);
        typeBuilder.add("countyCode", String.class);
        typeBuilder.add("county", String.class);
        typeBuilder.add("town", String.class);
        typeBuilder.add("townCode", String.class);
        typeBuilder.add("longitude", Double.class);
        typeBuilder.add("latitude", Double.class);
        //聚合属性
        typeBuilder.add("maxX", Double.class);
        typeBuilder.add("maxY", Double.class);
        typeBuilder.add("minX", Double.class);
        typeBuilder.add("minY", Double.class);
        typeBuilder.add("count", Integer.class);
        featureType = typeBuilder.buildFeatureType();
        return featureType;
    }

    private SimpleFeatureBuilder getFeatureBuilder() {
        if (featureType == null)
            getFeatureType();
        return new SimpleFeatureBuilder(featureType);
    }

    public SimpleFeature getFeature(PointResponse point){
        SimpleFeatureBuilder featureBuilder = getFeatureBuilder();
        Object[] obj={

        };
        return featureBuilder.buildFeature(String.valueOf(point.getId()),obj);
    }
}
