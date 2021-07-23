# Java SSM练手小项目--基于SSM框架的人力资源管理后台系统

这是我在网上找的一个ssm小项目，总的来说基本上应用到了之前所学的大部分内容，项目整体代码量也不大。因为其实写完配置文件之后，业务层代码基本上都是做CRUD操作，重复性较多，所以只完成了Employee相关的功能。

原博主CSDN链接：https://blog.csdn.net/noaman_wgs/article/details/79503559

github源码：https://github.com/GenshenWang/SSM_HRMS.git

用到的技术栈有：

- 框架：SSM
- 数据库：MySQL
- 前端框架：Bootstrap快速搭 搭建JSP页面（我这里直接copy的原博主的代码）
- 项目管理：MAVEN
- 开发工具：Intellij IDEA
- 开发环境：Windows



## 项目整体架构：

![image-20210723174713008](C:\Users\Administrator\Desktop\新建文件夹 (2)\SSM-HRMS\SSM项目.assets\image-20210723174713008.png)

![image-20210723174737994](C:\Users\Administrator\Desktop\新建文件夹 (2)\SSM-HRMS\SSM项目.assets\image-20210723174737994.png)



## 项目流程

### 1.创建数据库

```mysql
CREATE DATABASE `ssmhrms`;

DROP TABLE IF EXISTS `tbl_emp`;

CREATE TABLE `tbl_emp`(
`emp_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
`emp_name` VARCHAR(22) NOT NULL DEFAULT '',
`emp_email` VARCHAR(256) NOT NULL DEFAULT '',
`gender` CHAR(2) NOT NULL DEFAULT '',
`dept_id` INT(11) NOT NULL DEFAULT 0,
PRIMARY KEY(`emp_id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept`(
`dept_id` INT(11) NOT NULL DEFAULT 0,
`dept_name` VARCHAR(255) NOT NULL DEFAULT '',
`dept_leader` VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(`dept_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
```

### 2.创建maven工程

###### 1.导依赖包和设置静态资源导出，并且在工程结构下创建lib目录添加所有依赖。

（注意：很容易就会忘记添加lib目录，因为我习惯直接创建maven项目，然后添加web支持）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.kuang</groupId>
    <artifactId>ssmbuiled</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>16</maven.compiler.source>
        <maven.compiler.target>16</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.24</version>
        </dependency>
        <!--数据库连接池-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.5</version>
        </dependency>
        <!--Servlet - JSP -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.7</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
        <!--Spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.8</version>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <!--aop事务处理-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.7</version>
        </dependency>
    </dependencies>

    <!--静态资源导出问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>


</project>
```

###### 2.添加web支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--乱码过滤-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Session-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>

    <!-- 启动页面 -->
    <welcome-file-list>
        <welcome-file>/WEB-INF/jsp/login.jsp</welcome-file>
    </welcome-file-list>


</web-app>
```



###### 3.连接数据库,测试idea能否连接到数据库

![image-20210723171527655](C:\Users\Administrator\Desktop\新建文件夹 (2)\SSM-HRMS\SSM项目.assets\image-20210723171527655.png)

###### 4.配置核心文件

- applicationContext.xml（集合SSM框架所有的配置文件）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <import resource="classpath:spring-dao.xml"/>
    <import resource="classpath:spring-service.xml"/>
    <import resource="classpath:spring-mvc.xml"/>


    <!--拦截器配置-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--/**:包括这个请求下面的所有请求-->
            <mvc:mapping path="/**"/>
            <bean class="com.ssmhrms.config.LoginInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
</beans>
```

- database.properties（连接数据库配置文件）

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssmhrms?useSSL=true&useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=123456
```

- Mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration核心配置文件-->
<configuration>

    <!--标准的日志工厂实现-->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--是否开启自动驼峰命名规则，映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <!--配置数据源，交给Spring做-->

    <typeAliases>
        <package name="com.ssmhrms.pojo"/>
    </typeAliases>

    <mappers>
        <package name="com.ssmhrms.mapper"/>
    </mappers>

</configuration>
```

- spring-dao.xml （dao层相关的配置文件）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--1.关联数据库配置文件-->
    <context:property-placeholder location="classpath:database.properties"/>

    <!--2.连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!--c3p0连接池的私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动commit-->
        <property name="autoCommitOnClose" value="false"/>
        <!--获取连接超时时间-->
        <property name="checkoutTimeout" value="10000"/>
        <!--当获取连接失败重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--3.sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定Mybatis的配置文件-->
        <property name="configLocation" value="classpath:Mybatis-config.xml"/>
    </bean>

    <!--4.配置dao接口扫描包，动态的实现Dao接口可以注入到Spring容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入sqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--要扫描的dao包-->
        <property name="basePackage" value="com.ssmhrms.mapper"/>
    </bean>


</beans>
```

- spring-mvc.xml（控制层和视图层交互配置文件）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--1.注解驱动-->
    <mvc:annotation-driven/>
    <!--2.静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--3.扫描包controller-->
    <context:component-scan base-package="com.ssmhrms.controller"/>
    <!--4.视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

- spring-service.xml（业务层相关配置文件）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--1.扫描service下的包-->
    <context:component-scan base-package="com.ssmhrms.service"/>

    <!--2.将所有业务类，注入到spring中，可以通过配置或者注解实现-->
    <bean id="EmployeeServiceImpl" class="com.ssmhrms.service.EmployeeServiceImpl">
        <property name="employeeMapper" ref="employeeMapper"/>
    </bean>
    <bean id="DepartmentServiceImpl" class="com.ssmhrms.service.DepartmentServiceImpl">
        <property name="departmentMapper" ref="departmentMapper"/>
    </bean>

    <!--3.声明式事务配置-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--4.aop事务支持-->
    <!--结合AOP实现事务的织入-->
    <!--配置事务的通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--给哪些方法配置事务-->
        <!--配置事务的传播特性：new propagation-->
        <tx:attributes>
            <tx:method name="add" propagation="REQUIRED"/>
            <tx:method name="delete" propagation="REQUIRED"/>
            <tx:method name="update" propagation="REQUIRED"/>
            <tx:method name="query" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(* com.ssmhrms.mapper.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
    </aop:config>
</beans>
```

###### 5.创建pojo层

（根据数据表创建对应的javabean类）

- Department

```java
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
```

- Employee（Employee类中包含department属性，所以注意要手动重写有参构造器和tostring方法）

```java
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
```

###### 6.创建mapper层接口和mapper.xml配置文件

（这里先写的一些通用的增删改查操作，然后看前段页面中有哪些功能，对应的在添加需要的方法）

- DepartmentMapper

```java
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
```

- DepartmentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">



<mapper namespace="com.ssmhrms.mapper.DepartmentMapper">

    <delete id="deleteDeptById" parameterType="int">
        delete from ssmhrms.tbl_dept where dept_id=#{deptId};
    </delete>

    <update id="updateDeptById" parameterType="Department">
        update ssmhrms.tbl_dept set dept_name=#{deptName},dept_leader=#{deptLeader} where dept_id=#{deptId};
    </update>

    <insert id="insertDept" parameterType="Department">
        insert into ssmhrms.tbl_dept(dept_id, dept_name, dept_leader)
        values (#{deptId},#{deptName},#{deptLeader});
    </insert>

    <select id="queryDeptById" parameterType="int" resultType="Department">
        select * from ssmhrms.tbl_dept where dept_id=#{deptId};
    </select>

    <select id="queryDeptByName" resultType="Department">
        select * from ssmhrms.tbl_dept where dept_name=#{deptName};
    </select>

    <select id="queryAllDept" resultType="Department">
        select * from ssmhrms.tbl_dept;
    </select>

    <select id="countDept">
        select count(*) from ssmhrms.tbl_dept;
    </select>

</mapper>
```

- EmployeeMapper

```xml
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
```

- EmployeeMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<mapper namespace="com.ssmhrms.mapper.EmployeeMapper">

    <delete id="deleteEmpById" parameterType="int">
        delete
        from ssmhrms.tbl_emp
        where emp_id = #{empId};
    </delete>

    <update id="updateEmpById" parameterType="Employee">
        update ssmhrms.tbl_emp
        set emp_name=#{empName},
            emp_email=#{empEmail},
            gender=#{gender},
            dept_id=#{deptId}
        where emp_id = #{empId};
    </update>

    <insert id="insertEmp" parameterType="Employee">
        insert into ssmhrms.tbl_emp(emp_name, emp_email, gender, dept_id)
        values (#{empName}, #{empEmail}, #{gender}, #{deptId});
    </insert>

    <select id="queryEmpById" parameterType="int" resultType="Employee">
        select *
        from ssmhrms.tbl_emp
        where emp_id = #{empId};
    </select>

    <select id="queryEmpByName" resultType="Employee">
        select *
        from ssmhrms.tbl_emp
        where emp_name = #{empName};
    </select>

    <select id="queryEmpByIdWithDep" resultType="Employee">
        SELECT emp.emp_id, emp.emp_name, emp.emp_email, emp.gender, dept.dept_name
        FROM ssmhrms.tbl_emp emp
                 JOIN ssmhrms.tbl_dept dept ON emp.emp_id = dept.dept_id where emp.emp_id=#{empId};
    </select>

    <select id="queryAllEmp" parameterType="map" resultMap="EmpDept">
       select * from ssmhrms.tbl_emp emp join ssmhrms.tbl_dept dept on emp.emp_id=dept.dept_id limit #{offset},#{limit};;
    </select>
    <resultMap id="EmpDept" type="Employee">
        <result property="empId" column="emp_id"/>
        <result property="empName" column="emp_name"/>
        <result property="empEmail" column="emp_email"/>
        <result property="gender" column="gender"/>
        <association property="department" javaType="Department">
            <result property="deptId" column="dept_id"/>
            <result property="deptName" column="dept_name"/>
            <result property="deptLeader" column="dept_leader"/>
        </association>
    </resultMap>


    <select id="countEmp" resultType="int">
        select count(*) from ssmhrms.tbl_emp;
    </select>
</mapper>
```

###### 7.创建service层接口和接口的实体类

（接口可以直接复制mapper层的接口，把mapper中接口的方法中的参数注解去掉即可）

- DepartmentService

```java
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
```

- DepartmentServiceImpl

```java
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
```

- EmployeeService

```java
package com.ssmhrms.service;

import com.ssmhrms.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JIAJUN
 * @create 2021-07-22 16:58
 */
public interface EmployeeService {
    //按id删除员工
    int deleteEmpById(int empId);

    //按id修改
    int updateEmpById(Employee employee);

    //添加
    int insertEmp(Employee employee);

    //查询员工按id
    Employee queryEmpById(int empId);

    //查询员工按姓名
    Employee queryEmpByName(String empName);

    // 查询带有部门信息的Employee按Id
    Employee queryEmpByIdWithDep(int empId);

    //查询所有员工带部门名称
    List<Employee> queryAllEmp(Map<String,Integer> map);

    //查询总记录数
    int countEmp();
}
```

- EmployeeServiceImpl

```java
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
```

###### 8.创建controller层，与前端交互

（根据前段页面展示的功能来写controller层）

这里先写的是Login控制器，因为页面最开始是从登陆开始

- LoginController

```java
package com.ssmhrms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author JIAJUN
 * @create 2021-07-22 21:20
 */
@Controller
@RequestMapping("/hrms")
public class LoginController {

    @RequestMapping("/goLogin")
    public String goLogin() {
        return "login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        return "main";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password, HttpServletRequest request) {
        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("userLoginInfo", username);
            return "redirect:/hrms/main";
        } else {
            request.setAttribute("error", "用户名或者密码不正确");
            return "login";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("userLoginInfo");
        return "login";
    }
}
```

然后创建登陆拦截器，保证项目的安全性

- LoginInterceptor

```java
package com.ssmhrms.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author JIAJUN
 * @create 2021-07-22 21:55
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //放行：判断什么情况下登陆
        //登陆页面放行
        if (request.getRequestURI().contains("goLogin")){
            return true;
        }
        if (request.getRequestURI().contains("login")){
            return true;
        }

        if(session.getAttribute("userLoginInfo") != null){
            return true;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }
}
```

然后继续完善其他控制层功能，这里主要完成率员工相关的增删改查功能。

- EmployeeController

```java
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
```

- DepartmentController

```java
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
```



###### 9.创建Util工具类

- JsonMsg（因为这里的前段页面都是用的原博主的，所以就直接copy了他的工具类，这个工具类主要是用在前后端消息交互过程）

```java
package com.ssmhrms.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GenshenWang.nomico
 * @date 2018/3/7.
 */
public class JsonMsg {

    private int code;
    private String msg;
    private Map<String, Object> extendInfo = new HashMap<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public static JsonMsg success(){
        JsonMsg res = new JsonMsg();
        res.setCode(100);
        res.setMsg("操作成功！");
        return res;
    }

    public static JsonMsg fail(){
        JsonMsg res = new JsonMsg();
        res.setCode(200);
        res.setMsg("操作失败！");
        return res;
    }

    public JsonMsg addInfo(String key, Object obj){
        this.extendInfo.put(key, obj);
        return this;
    }

}
```

###### 10.改写jsp页面中方法的href链接指定的URL，因为与原博主的@RequestMapping路径有一些区别。



## 总结

1.遇到的第一个bug是项目加载完成后，服务器能够访问，但是加载输出文件中只有静态资源文件，没有加载java类文件，网上搜了相关问题也没找到很好的答案，最后是关闭idea，删除项目里的.idea文件夹，然后在重启idea并配置maven项目。

2.因为SSM框架中都是靠配置文件来完成我们需要的功能，所以很多配置我都是复制粘贴，这就需要特别的细心找出配置中的变量，比如绑定路径等。

3.因为前端不会所以只能copy别人的前段，所以导致里面很多超链接路径需要修改，我自己的做法是先写好登陆页面之后，去登陆页面找有哪些按钮，哪些功能，把这些地方分好类，写好增删改查对应的功能后去前端页面找超链接对应的地方然后修改。一个页面一个页面的推进。



最后也将我的第一个算独立完成的小项目的一部分代码传到我的gitHub当中，希望能给大家带来一些帮助。当然如果需要做完整的项目的话，可以去看原博主。



2021.07.23

Jerry-LYU