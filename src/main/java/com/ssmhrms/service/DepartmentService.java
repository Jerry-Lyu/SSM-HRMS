package com.ssmhrms.service;

import com.ssmhrms.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 18:25
 */
public interface DepartmentService {
    //删除按id
    int deleteDeptById(int deptId);

    //修改按id
    int updateDeptById(Department department);

    //添加
    int insertDept(Department department);


    //查询按id
    Department queryDeptById(int deptId);


    //查询按名字
    Department queryDeptByName(String deptName);

    //查询全部部门
    List<Department> queryAllDept();

    //分页
    List<Department> getDeptByLimit(Map<String,Integer> map);


    //查询总数
    int countDept();
}
