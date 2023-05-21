package com.zh.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.server.config.BasicConstants;
import com.zh.server.entity.MenuRole;
import com.zh.server.entity.Role;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.MenuRoleService;
import com.zh.server.server.MenuService;
import com.zh.server.server.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/")
    public ResponseBase<Role> getAllRoles() {
        return new ResponseBase().success(roleService.listALL());
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public ResponseBase addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return new ResponseBase().success("添加成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code, BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public ResponseBase deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return new ResponseBase().success("删除成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code, BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/menus")
    private ResponseBase getAllMenus() {
        return new ResponseBase().success(menuService.getAllMenus());
    }

    @ApiOperation(value = "根据角色ID查询菜单ID")
    @GetMapping("/mid/{rid}")
    private ResponseBase getMidByRid(@PathVariable Integer rid) {
        //启用多数据源后，mybatis-plus自带多方法找不到映射文件
//        return new ResponseBase().success(menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream()
//                .map(MenuRole::getMid).collect(Collectors.toList()));
        //单纯的将实体结果集-》mid的List集合（不去重）
        return new ResponseBase().success(menuRoleService.listByRid(rid).stream().map(MenuRole::getMid).collect(Collectors.toList()));
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public ResponseBase updateMenuRole(Integer rid, Integer[] mids) {
        return new ResponseBase().success(menuRoleService.updateMenuRole(rid, mids));
    }

}
