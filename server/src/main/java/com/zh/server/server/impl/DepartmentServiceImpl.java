package com.zh.server.server.impl;

import com.zh.server.config.BasicConstants;
import com.zh.server.entity.Department;
import com.zh.server.mapper.yyb.DepartmentMapper;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        //-1是顶层部分的parentId
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class) //主程序入口也要开启事务控制
    public ResponseBase addDep(Department dep) {
        dep.setEnabled(true);
        //执行存储过程
        departmentMapper.addDep(dep);
        if (1 == dep.getResult()) {
            return new ResponseBase().success(dep);
        }

        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code, BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class) //主程序入口也要开启事务控制
    public ResponseBase deleteDepartment(Integer id) {

        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if (-2 == department.getResult()) {
            return ResponseBase.failed(BasicConstants.HttpStatus.FORBIDDEN.code,"该部门下有子部门，删除失败！");
        }
        if (-1 == department.getResult()) {
            return ResponseBase.failed(BasicConstants.HttpStatus.FORBIDDEN.code,"该部门下有员工，删除失败！");
        }
        if (1 == department.getResult()) {
            return new ResponseBase().success(null);
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }
}
