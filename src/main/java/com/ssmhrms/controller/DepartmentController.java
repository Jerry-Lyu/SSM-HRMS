package com.ssmhrms.controller;

import com.ssmhrms.pojo.Department;
import com.ssmhrms.service.DepartmentService;
import com.ssmhrms.service.EmployeeService;
import com.ssmhrms.util.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author JIAJUN
 * @create 2021-07-22 19:00
 */
@Controller
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    @Qualifier("DepartmentServiceImpl")
    private DepartmentService departmentService;

     // 查询所有部门名称

    @RequestMapping(value = "/getDeptName")
    public JsonMsg getDeptName(){
        List<Department> departmentList = departmentService.queryAllDept();
        if (departmentList != null){
            return JsonMsg.success().addInfo("departmentList", departmentList);
        }
        return JsonMsg.fail();
    }

}
