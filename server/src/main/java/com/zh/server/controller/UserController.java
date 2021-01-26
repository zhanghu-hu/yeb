package com.zh.server.controller;


import com.zh.server.config.BasicConstants;
import com.zh.server.entity.User;
import com.zh.server.request.user.LoginRequest;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * <p>
 *  用户相关接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录，返回token")
    @PostMapping("/login")
    public ResponseBase login(@Validated @RequestBody LoginRequest loginRequest, HttpServletRequest request){
        return userService.login(loginRequest,request);
    }

    /**
     * 获取当前登录用户的信息
     * @param principal security框架里的主体信息
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/information")
    public ResponseBase information(Principal principal){
        if (principal==null){
            //401
            return ResponseBase.failed(BasicConstants.HttpStatus.PERMISSION_DENIED.code,BasicConstants.HttpStatus.PERMISSION_DENIED.msg);
        }
        String username=principal.getName();
        User user=userService.getUserByUsername(username);
        user.setTPassword(null);
        user.setRoles(userService.getRolesByAdminId(user.getTId()));
        return ResponseBase.success(user);
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/add")
    public ResponseBase add(@Validated @RequestBody User user){
        return userService.add(user);
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public ResponseBase logout(){
        //前端拿到状态码，直接删除请求头的token
        return ResponseBase.success(null);
    }
}
