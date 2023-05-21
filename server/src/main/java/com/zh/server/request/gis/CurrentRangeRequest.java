package com.zh.server.request.gis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 *  请求当前范围内的点位条件
 * </p>
 *
 * @author ZH
 * @since 2021-08-29
 */
@Data
@ApiModel(value = "CurrentRangeRequest",description = "请求当前范围内的点位")
public class CurrentRangeRequest {

    @ApiModelProperty(name = "minX",value = "最小经度",dataType = "Double",required = true)
    @NotEmpty(message = "最小经度不能为空")
    public Double minX;

    @ApiModelProperty(name = "minY",value = "最小纬度",dataType = "Double",required = true)
    @NotEmpty(message = "最小纬度不能为空")
    public Double minY;

    @ApiModelProperty(name = "maxX",value = "最大经度",dataType = "Double",required = true)
    @NotEmpty(message = "最大经度不能为空")
    public Double maxX;

    @ApiModelProperty(name = "maxY",value = "最大纬度",dataType = "Double",required = true)
    @NotEmpty(message = "最大纬度不能为空")
    public Double maxY;

    @ApiModelProperty(name = "zoom",value = "当前层级",dataType = "Integer",required = true)
    @NotEmpty(message = "当前层级")
    public Integer zoom;
}
