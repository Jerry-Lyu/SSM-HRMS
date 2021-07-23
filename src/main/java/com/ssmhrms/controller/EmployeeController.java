package com.ssmhrms.controller;

import com.ssmhrms.pojo.Employee;
import com.ssmhrms.service.EmployeeService;
import com.ssmhrms.util.JsonMsg;
import org.aspectj.weaver.reflect.IReflectionWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author JIAJUN
 * @create 2021-07-22 19:00
 */
@Controller
@RequestMapping("/emp")
public class EmployeeController {
    //controller层调service层
    @Autowired
    @Qualifier("EmployeeServiceImpl")
    private EmployeeService employeeService;


    //员工查询操作
    @RequestMapping("/getEmpList")
    public ModelAndView employeePage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){

        ModelAndView mv = new ModelAndView("employeePage");
        HashMap<String, Integer> map = new HashMap<>();
        int totalItems = employeeService.countEmp();
        int limit = 5;
        int offset = (pageNo-1)*limit;
        map.put("limit", limit);
        map.put("offset",offset);
        List<Employee> employees = employeeService.queryAllEmp(map);
        int totalPages = (totalItems % limit == 0) ? totalItems / limit : totalItems / limit +1;
        mv.addObject("employees", employees)
                .addObject("totalItems", totalItems)
                .addObject("totalPages", totalPages)
                .addObject("curPage", pageNo);
        return mv;
    }

    //员工删除操作
    @RequestMapping("/deleteEmp/{empId}")
    public JsonMsg deleteEmp(@PathVariable("empId") int empId){
        int res = 0;
        if (empId > 0){
            res = employeeService.deleteEmpById(empId);
        }
        if (res != 1){
            return JsonMsg.fail().addInfo("emp_del_error", "员工删除异常");
        }
        return JsonMsg.success();
    }

    //员工添加操作
    @RequestMapping("/addEmp")
    public JsonMsg addEmp(Employee employee){
        int i = employeeService.insertEmp(employee);
        if (i == 1){
            return JsonMsg.success();
        }else {
            return JsonMsg.fail();
        }

    }

    //查询员工是否存在操作
    @RequestMapping("/checkEmpExists")
    public JsonMsg isExit(@RequestParam("empName") String empName){
        //对输入的姓名与邮箱格式进行验证
        String regName = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regName)){
            return JsonMsg.fail().addInfo("name_reg_error", "输入姓名为2-5位中文或6-16位英文和数字组合");
        }
        Employee employee = employeeService.queryEmpByName(empName);
        if (employee != null){
            return JsonMsg.fail().addInfo("name_reg_error", "用户名重复");
        }else {
            return JsonMsg.success();
        }
    }

    //查询最新页数
    @RequestMapping(value = "/getTotalPages")
    public JsonMsg getTotalPage(){
        int totalItems = employeeService.countEmp();
        //获取总的页数
        int temp = totalItems / 5;
        int totalPages = (totalItems % 5 == 0) ? temp : temp+1;
        return JsonMsg.success().addInfo("totalPages", totalPages);
    }

}
