package com.zh.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("t_menu")
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1156086106985L;

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @JsonIgnore
    private Integer id;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @TableField("iconCls")
    @ApiModelProperty(value = "图标")
    private String iconCls;

    @TableField("keepAlive")
    @ApiModelProperty(value = "是否保持激活")
    private Boolean keepAlive;

    @TableField("requireAuth")
    @ApiModelProperty(value = "是否请求权限")
    private Boolean requireAuth;

    @TableField("parentId")
    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    /**
     * crud忽略字段
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子菜单")
    private List<Menu> children;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色列表")
    private List<Role> roles;
}
