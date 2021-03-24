package com.zh.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zh.server.config.BasicConstants;
import com.zh.server.entity.*;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "员工表")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsStatusService politicsStatusService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JoblevelService joblevelService;

    @ApiOperation(value = "获取所有员工（分页）")
    @GetMapping("/all")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDateScope", value = "在职时间数字", dataType = "LocalDate"),
            @ApiImplicitParam(name = "currentPage",value = "当前页",dataType = "Integer"),
            @ApiImplicitParam(name = "size",value = "每页多少条数据",dataType = "Integer")
    })
    public ResponseBase getEmployeeByPage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增职员")
    public ResponseBase addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> list = employeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表title", "员工表Sheet", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream outputStream = null;
        try {
            //流形式
            response.setHeader("content-type", "application/octet-stream");
            //中文乱码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush(); //将缓存的字节全部写入输出流，避免有一部分在缓存中导致客户端接收的不全
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据",notes = "上传")
    @ApiImplicitParam(name = "file", value = "上传文件", dataType = "MultipartFile")
    @PostMapping(value = "/import",produces = "application/json;charset=utf-8")
    public ResponseBase importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<Nation> nations = nationService.list();
        List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
        List<Position> positions = positionService.list();
        List<Department> departments = departmentService.list();
        List<Joblevel> joblevels = joblevelService.list();
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            list.forEach(employee -> {
                //民族id
                employee.setNationId(nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
                //政治面貌id
                employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                //部门id
                employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
                //职称id
                employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                //职位id
                employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
            });
            if (employeeService.saveBatch(list)) {
                return ResponseBase.success(null);
            }
            return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code, BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code, BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }
}
