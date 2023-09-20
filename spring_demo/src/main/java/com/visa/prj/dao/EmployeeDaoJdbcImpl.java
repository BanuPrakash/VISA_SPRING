package com.visa.prj.dao;

import com.visa.prj.entity.Employee;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Profile("dev")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee e) {
        System.out.println(e.getName() + " inserted in database ...");
    }
}
