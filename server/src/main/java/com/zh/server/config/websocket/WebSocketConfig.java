package com.zh.server.config.websocket;

import com.zh.server.config.security.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类
 *
 * @author ZH
 * @date 2021-01-31
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 添加这个Endpoint(端点)，这样网页可以通过WebSocket连接上服务
     * 相当于我们配置WebSocket的服务地址，并且可以指定是否使用socketJS
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1、addEndpoint("/ws/ep")：将ws/ep注册为stomp的端点，用户连接这个端点就可以进行WebSocket通讯，支持socketJS
         * 2、setAllowedOrigins("*")：支持跨域
         * 3、withSockJS()：支持SocketJS连接
         */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 附加步骤
     * jwt令牌验证
     * security+jwt的验证（输入通道参数配置）
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            /**
             * 预发送
             * @param message
             * @param channel
             * @return
             */
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                //判断是否为连接，如果是，则需要获取token并设置用户对象
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader(tokenHeader);
                    if (!StringUtils.isEmpty(token)){
                        String authToken=token.substring(tokenHead.length());
                        String username=jwtToken.getUsernameFromToken(authToken);
                        //token存在用户名
                        if (!StringUtils.isEmpty(username)){
                            //方法在框架中被重新，返回的信息是user实体
                            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                            //token是否有效
                            if (jwtToken.validateToken(authToken,userDetails)){
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                //为WebSocket设置对象
                                accessor.setUser(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 配置代理域，可以配置多个，配置代理目的地前缀为/queue，可以在配置域上向客户端推送消息
         * “/queue”下面可以接多个消息队列
         */
        registry.enableSimpleBroker("/queue");

        //如果不设置下面这一句，用convertAndSendToUser来发送消息，前端订阅只能用/user开头。
//        registry.setUserDestinationPrefix("/userTest");
        //客户端（html等）向服务端发送消息的前缀
//        registry.setApplicationDestinationPrefixes("/app");
    }
}
