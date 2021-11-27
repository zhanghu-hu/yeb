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
}
