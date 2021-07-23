package com.ssmhrms.mapper;

import com.ssmhrms.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 16:55
 */
public interface EmployeeMapper {

    //按id删除员工
    int deleteEmpById(@Param("empId") int empId);

    //按id修改
    int updateEmpById(@Param("employee") Employee employee);

    //添加
    int insertEmp(@Param("employee") Employee employee);

    //查询员工按id
    Employee queryEmpById(@Param("empId") int empId);

    //查询员工按姓名
    Employee queryEmpByName(@Param("empName") String empName);

    // 查询带有部门信息的Employee按Id
    Employee queryEmpByIdWithDep(@Param("empId") int empId);

    //查询所有员工带部门名称
    List<Employee> queryAllEmp(Map<String,Integer> map);


    //查询总记录数
    int countEmp();


}
