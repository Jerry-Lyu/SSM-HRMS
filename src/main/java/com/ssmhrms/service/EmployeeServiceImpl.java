package com.ssmhrms.service;

import com.ssmhrms.mapper.EmployeeMapper;
import com.ssmhrms.pojo.Employee;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 16:58
 */
public class EmployeeServiceImpl implements EmployeeService{
    //service调dao层：组合dao
    private EmployeeMapper employeeMapper;
    public void setEmployeeMapper(EmployeeMapper employeeMapper){
        this.employeeMapper=employeeMapper;
    }


    @Override
    public int deleteEmpById(int empId) {
        return employeeMapper.deleteEmpById(empId);
    }

    @Override
    public int updateEmpById(Employee employee) {
        return employeeMapper.updateEmpById(employee);
    }

    @Override
    public int insertEmp(Employee employee) {
        return employeeMapper.insertEmp(employee);
    }

    @Override
    public Employee queryEmpById(int empId) {
        return employeeMapper.queryEmpById(empId);
    }

    @Override
    public Employee queryEmpByName(String empName) {
        return employeeMapper.queryEmpByName(empName);
    }

    @Override
    public Employee queryEmpByIdWithDep(int empId) {
        return employeeMapper.queryEmpByIdWithDep(empId);
    }

    @Override
    public List<Employee> queryAllEmp(Map<String,Integer> map) {
        return employeeMapper.queryAllEmp(map);
    }

    @Override
    public int countEmp() {
        return employeeMapper.countEmp();
    }
}
