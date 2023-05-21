package com.zh.server.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author ZH
 * @date 2021-01-23
 */
@Api(tags = "GIS相关处理")
@RestController
public class TestController {

    @RequestMapping(value = "/employee/basic/menu1",method = RequestMethod.GET)
    public String getMenu1(){
        return "/employee/basic/menu1";
    }

    @RequestMapping(value = "/employee/advanced/menu2",method = RequestMethod.GET)
    public String getMenu2(){
        return "/employee/advanced/menu2";
    }
}
