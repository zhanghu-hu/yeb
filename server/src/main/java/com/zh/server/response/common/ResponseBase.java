package com.zh.server.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公告返回对象
 * @author ZH
 * @date 2021/1/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBase<T> {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功
     * @param obj
     * @return
     */
    public ResponseBase success(T obj){
        return new ResponseBase(200,"成功",obj);
    }

    /**
     * 失败
     * @param code
     * @param mess
     * @return
     */
    public static ResponseBase failed(Integer code,String mess){
        return new ResponseBase(code,mess,null);
    }
}
