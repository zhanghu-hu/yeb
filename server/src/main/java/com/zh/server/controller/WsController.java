package com.zh.server.controller;

import com.zh.server.entity.User;
import com.zh.server.response.webSocket.ChatMsg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * WebSocket 发送聊天消息
 * @author ZH
 * @date 2021-01-30
 */
@Api(tags = "WebSocket")
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Authentication在webSocket配置文件校验jwt时赋值
     * @param authentication
     * @param chatMsg
     */
    @MessageMapping("/ws/chat") //浏览器向服务器发送信息的地址
    @SendTo("/send/to") //当服务器有信息时，向订阅了“/send/to”的浏览器发消息
    public void handleMsg(Authentication authentication, ChatMsg chatMsg){
        User user=(User) authentication.getPrincipal();
        chatMsg.setFrom(user.getTUsername());
        chatMsg.setFormNickName(user.getTName());
        chatMsg.setDate(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);//将消息转到/queue/chat
    }
}
