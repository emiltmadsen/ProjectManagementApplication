package com.example.demo.controllers;

import com.example.demo.dto.ChartData;
import com.example.demo.dto.EmployeeProject;
import com.example.demo.entities.Project;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Value("${version}")
    private String ver;

    // Field Injection
    @Autowired
    ProjectRepository proRepo;

    // Field Injection
    @Autowired
    EmployeeRepository empRepo;

    @GetMapping("/")
    public String displayHome(Model model) throws JsonProcessingException {

        model.addAttribute("versionNumber", ver);

        Map<String, Object> map = new HashMap<>();

        // Querying the database for projects
        List<Project> projects = proRepo.findAll();
        model.addAttribute("projectsList", projects);

        List<ChartData> projectData = proRepo.getProjectStatus();

        // Converting projectData object into a json structure for use in js
        ObjectMapper objectMapper = new ObjectMapper();

        // Produces this [["NOTSTARTED", 1], ["INPROGRESS, 2], ["COMPLETED", 1]]
        String jsonString = objectMapper.writeValueAsString(projectData);


        // Adds the jsonString to the model and therefore to "/"
        model.addAttribute("projectStatusCnt", jsonString);

        List<EmployeeProject> employeesProjectCnt = empRepo.employeeProjects();
        model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);

        return "home";
    }

}
