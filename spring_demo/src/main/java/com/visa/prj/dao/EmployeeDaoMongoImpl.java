package com.visa.prj.dao;

import com.visa.prj.entity.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class EmployeeDaoMongoImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee e) {
        System.out.println("Mongo store of :" + e.getName());
    }
}
