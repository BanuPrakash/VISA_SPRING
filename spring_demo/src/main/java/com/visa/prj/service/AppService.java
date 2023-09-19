package com.visa.prj.service;

import com.visa.prj.dao.EmployeeDao;
import com.visa.prj.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    @Autowired
    private EmployeeDao employeeDao;

    public  void insert(Employee e) {
        employeeDao.addEmployee(e);
    }
}
