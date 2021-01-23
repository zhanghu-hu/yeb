package com.zh.server.controller;


import com.zh.server.entity.Menu;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.MenuService;
import com.zh.server.server.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "菜单")
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单
     * @return
     */
    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public ResponseBase getMenuByUserID(){
        //用户id从security框架自带的userDetails（配置类中重写了，等于User）中获取（通过用户名获取，登录的时候放到上下文对象里了）
        List<Menu> menuList=menuService.getMenuBuUserID();
        return ResponseBase.success(menuList);
    }
}
