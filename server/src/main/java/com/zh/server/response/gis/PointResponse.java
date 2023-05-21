package com.zh.server.response.gis;

import com.zh.server.entity.Nationwide;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 返回指定范围的点位
 * </p>
 *
 * @author ZH
 * @since 2021-08-29
 */
@Data
@ApiModel(value = "CurrentRangeRequest", description = "返回指定范围的点位")
public class PointResponse extends Nationwide {

    @ApiModelProperty(name = "count", value = "聚合数量", dataType = "Integer")
    public Integer count;

    @ApiModelProperty(name = "maxX", value = "外接maxX", dataType = "Double")
    public Double maxX;

    @ApiModelProperty(name = "maxY", value = "外接maxY", dataType = "Double")
    public Double maxY;

    @ApiModelProperty(name = "minX", value = "外接minX", dataType = "Double")
    public Double minX;

    @ApiModelProperty(name = "minY", value = "外接minY", dataType = "Double")
    public Double minY;
}
