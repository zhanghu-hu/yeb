package com.zh.server.server.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.server.entity.Employee;
import com.zh.server.mapper.EmployeeMapper;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.response.common.page.PageInfo;
import com.zh.server.server.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResponseBase getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate... beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        PageInfo pageInfo = new PageInfo(employeeIPage.getTotal(), employeeIPage.getRecords());
        return ResponseBase.success(pageInfo);
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
