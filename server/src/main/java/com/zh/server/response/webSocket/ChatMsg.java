package com.zh.server.response.webSocket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * WebSocket消息响应实体
 * @author ZH
 * @date 2021-01-31
 */
@ApiModel(value = "消息响应实体",description = "WebSocket消息")
@Data
@EqualsAndHashCode(callSuper = false) //equals：不做父类的比较  hashCode：生成时不考虑父类
@Accessors(chain = true)
public class ChatMsg {

    @ApiModelProperty(name = "from",value = "发送者",dataType = "String")
    private String from;

    @ApiModelProperty(name = "to",value = "接收者",dataType = "String")
    private String to;

    @ApiModelProperty(name = "content",value = "发送内容",dataType = "String")
    private String content;

    /**
     * LocalDateTime：线程安全、操作方便。LocalDate+LocalTime
     */
    @ApiModelProperty(name = "date",value = "发送时间",dataType = "LocalDateTime")
    private LocalDateTime date;

    @ApiModelProperty(name = "formNickName",value = "发送者昵称",dataType = "String")
    private String formNickName;
}
