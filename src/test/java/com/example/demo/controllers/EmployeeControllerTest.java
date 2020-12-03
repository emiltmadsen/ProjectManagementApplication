package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
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
//import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Note to all test classes:
 * Triple A UNIT Testing: Arrange, Act Assert. This structure is followed logically in all tests
 * Testing is done, utilizing the Mockito Framework
 * to ease mocking of dependent-class-functionality.
 *
 * KEA urges us to do unit tests: integration tests are brilliant, but beyond my capability.
 * Tests written by KCN are unit tests.
 *
 * Tests of the controller classes focus on the delivery of correct urls and inclusion of
 * data in the Model objects that will be passed to html-views.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeControllerTest {
    /* Creating a mock of something implies that you create a shallow shell that acts as
     * another - intended - object in just enough ways to stand in for that object. */

    @Mock
    EmployeeRepository empRepo;

    @Mock
    Model mockModel; // mocked because it is present in all tested method as parameter.

    @Mock Employee testEmployee;

    EmployeeController employeeController;

    /**
     * setUp method implements the ARRANGE aspect of Triple A-testing: Setting up
     * the data needed to the the tests. (@AfterEach also exists, but we don't need it.)
     */
    @BeforeEach
    void setUp() throws Exception {
        // Just mocked for good measure; no methods need mocking
        testEmployee = mock(Employee.class);

        // this is to decouple the functionality of the repo from the controller; so we test only the controller.
        empRepo = mock(EmployeeRepository.class);
        when(empRepo.findAll()).thenReturn(new ArrayList<>()); // making mocked findAll to return a sensibly empty list.

        employeeController = new EmployeeController();
        employeeController.setEmpRepo(empRepo); // dependency injection of mocked repo

        mockModel = mock(Model.class); // decoupling Model class from specific test

    }

    /**
     * Testing:
     * 1) Correct path to html-file returned
     * 2) Verify that employee repo is called 1 time for all members
     * 3) Verify that employee data is added to model */
    @Test
    void testDisplayEmployees() {
        // check that the right url is returned
        // The ACT aspect of the test happens in the call to the employeeController.disp...
        assertEquals("employees/list-employees", employeeController.displayEmployees(mockModel));
        // check if findAll is pulled
        verify(empRepo, times(1)).findAll();
        // check if addAttribute is pulled with parameters: ( "employees", new ArrayList )
        verify(mockModel, times(1)).addAttribute("employees", new ArrayList<>());
    }

    /**
     * Testing:
     * 1) Correct url to html is returned.
     * 2) Verify that employee data is added to Model.*/
    @Test
    void displayEmployeeForm() {
        assertEquals("employees/new-employee", employeeController.displayEmployeeForm(mockModel));
        verify(mockModel,times(1)).addAttribute(matches("employee"),any(Employee.class));
    }

    /**
     * Testing:
     * 1) Verify that correct string is returned
     * 2) Verify that new employee is persisted to repository */
    @Test
    void createEmployee() {
        assertEquals("redirect:/employees/new",employeeController.createEmployee(testEmployee, mockModel));
        verify(empRepo,times(1)).save(testEmployee);
    }
}