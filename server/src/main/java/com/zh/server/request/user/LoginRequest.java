package com.zh.server.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 *  登录实体
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
@Data
@ApiModel(value = "LoginRequest",description = "登录请求实体")
public class LoginRequest {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username",value = "用户名",dataType = "String",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "password",value = "密码",dataType = "String",required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(name = "code",value = "验证码",dataType = "String",required = true)
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
