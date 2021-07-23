package com.ssmhrms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JIAJUN
 * @create 2021-07-22 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int deptId;
    private String deptName;
    private String deptLeader;
}
