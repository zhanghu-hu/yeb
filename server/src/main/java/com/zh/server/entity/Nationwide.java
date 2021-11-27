package com.zh.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_appraise")
@ApiModel(value="Appraise对象", description="")
public class Nationwide implements Serializable {
    private static final long serialVersionUID = 21321457562343L;

    @TableId("t_id")
    @ApiModelProperty(value = "主键")
    private Integer id;

    @TableField("t_province")
    @ApiModelProperty("省级")
    private String province;

    @TableField("t_city")
    @ApiModelProperty("市级")
    private String city;

    @TableField("t_city_code")
    @ApiModelProperty("市级代码")
    private String cityCode;

    @TableField("t_county_code")
    @ApiModelProperty("县级代码")
    private String countyCode;

    @TableField("t_county")
    @ApiModelProperty("县级")
    private String county;

    @TableField("t_town")
    @ApiModelProperty("乡镇级")
    private String town;

    @TableField("t_town_code")
    @ApiModelProperty("乡镇级代码")
    private String townCode;

    @TableField("t_longitude")
    @ApiModelProperty("经度")
    private Double longitude;

    @TableField("t_latitude")
    @ApiModelProperty("纬度")
    private Double latitude;

    @TableField("t_geohash")
    @ApiModelProperty("GeoHah值")
    private String geoHash;
}
