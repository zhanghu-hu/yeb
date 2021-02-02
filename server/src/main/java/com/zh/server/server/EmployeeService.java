package com.zh.server.server;

import com.zh.server.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.server.response.common.ResponseBase;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    ResponseBase getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate... beginDateScope);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 新增职员
     * @param employee
     * @return
     */
    ResponseBase addEmp(Employee employee);
}
