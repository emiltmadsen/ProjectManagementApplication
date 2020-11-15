package com.example.demo.services;

import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    // Field injection
    @Autowired
    EmployeeRepository empRepo;


    // Constructor injection
    /* public EmployeeService(EmployeeRepository empRepo) {
        super();
        this.empRepo = empRepo;
    }*/

    // Setter injection
    /*@Autowired
    public void setEmpRepo(EmployeeRepository empRepo) {
        this.empRepo = empRepo;
    }*/
}
