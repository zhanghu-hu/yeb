package com.zh.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  用户表
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户实体")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "t_id", type = IdType.AUTO)
    private Integer tId;

    @ApiModelProperty(value = "名字")
    private String tName;

    @ApiModelProperty(value = "电话号码")
    private String tPhone;

    @ApiModelProperty(value = "地址")
    private String tAddress;

    @ApiModelProperty(value = "是否启用")
    @Getter(AccessLevel.NONE) //不生成get方法
    private Boolean tEnable;

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String tUsername;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    private String tPassword;

    @ApiModelProperty(value = "用户头像")
    private String tUserFace;

    @ApiModelProperty(value = "备注")
    private String tRemark;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return tPassword;
    }

    @Override
    public String getUsername() {
        return tUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return tEnable;
    }
}
