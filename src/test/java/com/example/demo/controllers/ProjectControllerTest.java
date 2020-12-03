package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Project;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * For comments on test classes, see EmployeeControllerTest.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectControllerTest {

    @Mock
    ProjectRepository proRepo;

    @Mock
    EmployeeRepository empRepo;

    @Mock
    Project mockProject;

    @Mock
    Model mockModel;

    ProjectController projectController; // test subject

    // Arranges data common between tests
    @BeforeEach
    void setUp() {
        mockProject = mock(Project.class);
        mockModel = mock(Model.class);

        // mocking repositories including all methods that will be accessed in tests
        proRepo = mock(ProjectRepository.class);
        when(proRepo.findAll()).thenReturn(new ArrayList<>());
        when(proRepo.save(mockProject)).thenReturn(mockProject);

        empRepo = mock(EmployeeRepository.class);
        when(empRepo.findAll()).thenReturn(new ArrayList<>());

        projectController = new ProjectController();
        projectController.setProjectRepository(proRepo);
        projectController.setEmployeeRepository(empRepo);
    }

    /**
     * Testing:
     * 1) Return of appropriate url
     * 2) A single call to repository for all projects
     * 3) Addition of "projects", a list of Projects to model
     */
    @Test
    void displayProjects() {
        assertEquals("projects/list-projects", projectController.displayProjects(mockModel));
        verify(proRepo, times(1)).findAll();
        verify(mockModel, times(1)).addAttribute("projects", new ArrayList<>());
    }

    /**
     * Testing:
     * 1) Return of appropriate url
     * 2) Call to employee repository findAll() - to fetch all persisted employees.
     * 3) Addition of pairs
     * "allEmployees" , a List < Employee >
     * "project" , a Project
     * to model.
     */
    @Test
    void displayProjectForm() {
        assertEquals("projects/new-project",projectController.displayProjectForm(mockModel));
        verify(empRepo, times(1)).findAll();
        verify(mockModel, times(1)).addAttribute("allEmployees", new ArrayList<>());
        verify(mockModel, times(1)).addAttribute(matches("project"),any(Project.class));
    }

    /**
     * Testing:
     * 1) Return of good url
     * 2) Call to save() on project repository
     * */
    @Test
    void createProjectForm() {
        assertEquals("redirect:/projects",projectController.createProjectForm(mockProject,mockModel));
        verify(proRepo,times(1)).save(mockProject);
    }
}