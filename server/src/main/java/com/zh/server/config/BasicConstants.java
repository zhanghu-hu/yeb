package com.zh.server.config;

/**
 * 常量集合
 * @author ZH
 * @date 2021-01-20
 */
public class BasicConstants {

    /**
     * 响应码 400在全局异常里用掉了
     */
    public enum HttpStatus {

        SUCCESS(200, "成功"),
        ENTITY_ERROR(300,"实体校验异常"),
        PERMISSION_DENIED(401, "没有权限"),
        INTERNAL_SERVER_ERROR(500, "服务端错误"),
        USERNAME_OR_PASSWORD_ERROR(600,"用户名或密码不对"),
        FORBID_USER(700,"账号被禁用，请联系管理员"),
        NO_LOGIN(800,"用户未登录或凭证过期"),
        USERNAME_EXIST(810,"用户名已存在"),
        NO_CODE(900,"验证码不正确");

        public int code;
        public String msg;

        HttpStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
