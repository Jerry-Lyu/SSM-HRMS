package com.ssmhrms.mapper;

import com.ssmhrms.pojo.Department;
import com.ssmhrms.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 17:55
 */
public interface DepartmentMapper {

    //删除按id
    int deleteDeptById(@Param("deptId") int deptId);

    //修改按id
    int updateDeptById(@Param("department") Department department);

    //添加
    int insertDept(@Param("department") Department department);


    //查询按id
    Department queryDeptById(@Param("deptId") int deptId);


    //查询按名字
    Department queryDeptByName(@Param("deptName") String deptName);

    //查询全部部门
    List<Department> queryAllDept();

    //分页
    List<Department> getDeptByLimit(Map<String,Integer> map);


    //查询总数
    int countDept();

}
