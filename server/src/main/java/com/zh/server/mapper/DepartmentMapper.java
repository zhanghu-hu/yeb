package com.zh.server.mapper;

import com.zh.server.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取全部部门
     * @param i
     * @return
     */
    List<Department> getAllDepartmentsByParentId(@Param("ID") int i);

    /**
     * 添加部门（牵连的数据表较多，使用了存储过程）
     * @param dep
     */
    void addDep(@Param("dep") Department dep);

    /**
     * 删除部门（调用了带判断的存储过程）
     * @param dep
     */
    void deleteDepartment(@Param("dep") Department dep);
}
