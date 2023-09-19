package com.visa.prj.dao;

import com.visa.prj.entity.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee e) {
        System.out.println(e.getName() + " inserted in database ...");
    }
}
