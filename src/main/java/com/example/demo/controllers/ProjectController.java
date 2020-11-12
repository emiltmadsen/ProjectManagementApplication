package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Project;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectRepository proRepo;

    @Autowired
    EmployeeRepository empRepo;

    // An empty getmapping maps to the requestmapping for the controller class (in this case /projects)
    @GetMapping
    public String displayProjects(Model model) {
        List<Project> projects = proRepo.findAll();
        model.addAttribute("projects", projects);
        return "projects/list-projects";
    }

    @GetMapping("/new")
    public String displayProjectForm(Model model) {

        Project aProject = new Project();
        List<Employee> employees = empRepo.findAll();
        model.addAttribute("allEmployees", employees);
        model.addAttribute("project", aProject);

        return "projects/new-project";
    }

    // This handles the saving to the h2 database
    @PostMapping("/save")
    public String createProjectForm(Project project, Model model) {
        proRepo.save(project);

        // Use a redirect to prevent duplicate submissions
        return "redirect:/projects";
    }

}
