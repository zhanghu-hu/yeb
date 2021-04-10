package com.zh.server.controller;


import com.zh.server.entity.Department;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "部门（存储过程/mybatis逐级递归）")
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "获取所有部门（mybatis逐级递归）")
    @GetMapping("/all")
    public ResponseBase getAllDepartments(){

        return new ResponseBase().success(departmentService.getAllDepartments());
    }

    @ApiOperation(value = "添加部门（牵连的数据表较多，使用了存储过程）")
    @PostMapping("/")
    public ResponseBase addDep(@RequestBody Department department){

        return departmentService.addDep(department);
    }

    @ApiOperation(value = "删除部门(调用了带判断的存储过程)")
    @DeleteMapping("/{id}")
    public ResponseBase deleteDep(@PathVariable Integer id){

        return departmentService.deleteDepartment(id);
    }
}
