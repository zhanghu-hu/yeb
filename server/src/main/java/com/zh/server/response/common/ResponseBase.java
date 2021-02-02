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
public class ResponseBase {

    /**
     * 返回码
     */
    public Integer code;

    /**
     * 返回信息
     */
    public String message;

    /**
     * 返回数据
     */
    public Object data;

    /**
     * 成功
     * @param obj
     * @return
     */
    public static ResponseBase success(Object obj){
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
