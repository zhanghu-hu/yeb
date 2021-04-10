package com.zh.server.server.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.server.config.BasicConstants;
import com.zh.server.entity.Employee;
import com.zh.server.mapper.EmployeeMapper;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.response.page.PageInfo;
import com.zh.server.server.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponseBase getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate... beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        PageInfo pageInfo = new PageInfo(employeeIPage.getTotal(), employeeIPage.getRecords());
        return new ResponseBase().success(pageInfo);
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    @Override
    public ResponseBase addEmp(Employee employee) {
        //处理合同期限成年，保留二位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat=new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.0)));
        if (employeeMapper.insert(employee)==1){
            //发送邮件
            Employee emp=employeeMapper.selectById(employee.getId());
            rabbitTemplate.convertAndSend("mail.welcome",emp);

            return new ResponseBase().success(null);
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }
}
