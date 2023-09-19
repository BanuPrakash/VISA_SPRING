package com.visa.prj.service;

import com.visa.prj.dao.EmployeeDao;
import com.visa.prj.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private  EmployeeDao employeeDao; // constructor dependency injection

    @Autowired
    public void setDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    public  void insert(Employee e) {
        employeeDao.addEmployee(e);
    }
}
