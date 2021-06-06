package com.zh.server.controller;

import com.zh.server.entity.User;
import com.zh.server.response.webSocket.ChatMsg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * WebSocket 发送聊天消息
 *
 * @author ZH
 * @date 2021-01-30
 */
@Api(tags = "WebSocket")
@Controller
@EnableScheduling
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播接口，实现向所有订阅对象广播
     */
    @Scheduled(fixedDelay = 5000)//spring的计划任务，这里表示每五秒执行一次，需要在配置类或者启动类上加一个注解@EnableScheduling，这样才能生效
    public void mes() {
        ChatMsg msg = new ChatMsg();
        msg.setContent("广播测试");
        simpMessagingTemplate.convertAndSend("/queue", msg);
    }

    /**
     * Authentication在webSocket配置文件校验jwt时赋值
     *
     * @param authentication
     * @param chatMsg
     */
    @MessageMapping("/ws/chat") //浏览器向服务器发送信息的地址
    @SendTo("/send/to") //当服务器有信息时，向订阅了“/send/to”的浏览器发消息
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        //设置发送消息的数据主题
        User user = (User) authentication.getPrincipal();
        chatMsg.setFrom(user.getTUsername());
        chatMsg.setFormNickName(user.getTName());
        chatMsg.setDate(LocalDateTime.now());

        //客户端需要在连接成功后去订阅（默认加上user，即/user/queue/chat），才能正确拿到服务器返回的数据
        //第一个参数是需要转发到到人（用户名），在验证token时已经加入
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);//将消息转到/queue/chat
    }
}
