package com.zh.server.server;

import com.zh.server.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.server.response.common.ResponseBase;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @param department
     * @return
     */
    ResponseBase addDep(Department department);

    /**
     * 删除部门(调用了带判断的存储过程)
     * @param id
     * @return
     */
    ResponseBase deleteDepartment(Integer id);
}
