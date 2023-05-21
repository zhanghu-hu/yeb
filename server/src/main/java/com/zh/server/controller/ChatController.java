package com.zh.server.controller;

import com.zh.server.entity.User;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线聊天
 * @author ZH
 * @date 2021-01-30
 */
@Api(tags = "在线聊天")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/allUser")
    public ResponseBase<User> getAllUsers(String keywords){
        return new ResponseBase().success(userService.getAllUsers(keywords));
    }
}
