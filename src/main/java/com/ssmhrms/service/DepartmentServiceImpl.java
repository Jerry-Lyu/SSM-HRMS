package com.ssmhrms.service;

import com.ssmhrms.mapper.DepartmentMapper;
import com.ssmhrms.pojo.Department;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 18:26
 */
public class DepartmentServiceImpl implements DepartmentService{

    //service调dao层：组合dao
    private DepartmentMapper departmentMapper;
    public void setDepartmentMapper(DepartmentMapper departmentMapper){
        this.departmentMapper = departmentMapper;
    }


    @Override
    public int deleteDeptById(int deptId) {
        return departmentMapper.deleteDeptById(deptId);
    }

    @Override
    public int updateDeptById(Department department) {
        return departmentMapper.updateDeptById(department);
    }

    @Override
    public int insertDept(Department department) {
        return departmentMapper.insertDept(department);
    }

    @Override
    public Department queryDeptById(int deptId) {
        return departmentMapper.queryDeptById(deptId);
    }

    @Override
    public Department queryDeptByName(String deptName) {
        return departmentMapper.queryDeptByName(deptName);
    }

    @Override
    public List<Department> queryAllDept() {
        return departmentMapper.queryAllDept();
    }

    @Override
    public List<Department> getDeptByLimit(Map<String, Integer> map) {
        return departmentMapper.getDeptByLimit(map);
    }


    @Override
    public int countDept() {
        return departmentMapper.countDept();
    }
}
