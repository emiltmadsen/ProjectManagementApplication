package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository empRepo;

    @GetMapping
    public String displayEmployees(Model model) {
        List<Employee> employees = empRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    @GetMapping("/new")
    public String displayEmployeeForm(Model model) {

        Employee anEmployee = new Employee();
        model.addAttribute("employee", anEmployee);

        return "employees/new-employee";
    }

    // This handles the saving to the h2 database
    @PostMapping("/save")
    public String createEmployee(Employee employee, Model model) {
        empRepo.save(employee);

        // Use a redirect to prevent duplicate submissions
        return "redirect:/employees/new";
    }

    /* TODO: Verify addition of setter for EmployeeRepository in EmployeeController.
    *   Necessary for testing (other option; get rid of Autowired and create parameterized
    *   constructor.)*/
    /** Setter for EmployeeRepository, added by Kian to allow test mockup */
    public void setEmpRepo(EmployeeRepository employeeRepository) {
        empRepo = employeeRepository;
    }

}
