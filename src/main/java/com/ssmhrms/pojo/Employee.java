package com.ssmhrms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JIAJUN
 * @create 2021-07-22 16:49
 */
@Data
@NoArgsConstructor
public class Employee {
    private int empId;
    private String empName;
    private String empEmail;
    private String gender;

    private Department department;

    public Employee(int empId, String empName, String empEmail, String gender) {
        this.empId = empId;
        this.empName = empName;
        this.empEmail = empEmail;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", gender='" + gender + '\'' +
                ", department=" + department.toString() +
                '}';
    }
}
