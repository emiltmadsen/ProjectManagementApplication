package com.example.demo.controllers;

import com.example.demo.dto.ChartData;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
class HomeControllerTest {

    @Mock
    ProjectRepository proRepo;

    @Mock
    EmployeeRepository empRepo;

    @Mock
    Model mockModel;

    @Autowired
    HomeController homeController;

    @BeforeEach
    void setUp() {

        proRepo = mock(ProjectRepository.class);
        empRepo = mock(EmployeeRepository.class);
        mockModel = mock(Model.class);

        when(proRepo.findAll()).thenReturn(new ArrayList<>());
        when(empRepo.findAll()).thenReturn(new ArrayList<>());

        ChartData data1 = getChartData("NOTSTARTED",1); // technically 1 is needed
        ChartData data2 = getChartData("INPROGRESS",2);
        ChartData data3 = getChartData("COMPLETED",1);
        ArrayList<ChartData> dummyChartData = new ArrayList<ChartData>();
        dummyChartData.add(data1);
        dummyChartData.add(data2);
        dummyChartData.add(data3);


        when(proRepo.getProjectStatus()).thenReturn(dummyChartData);

        homeController = new HomeController();
        homeController.setEmpRepo(empRepo);
        homeController.setProRepo(proRepo);
    }

    private ChartData getChartData(String label, int value) {
        return new ChartData() {
            @Override
            public String getLabel() {
                return label;
            }

            @Override
            public long getValue() {
                return value;
            }
        };
    }

    /**
     * Purpose of test is to ensure that the right data has been included in the Model
     * to be sent of the to view.
     * Since there is no branching logic locally in the method, there is no variance to test
     * so the test remains basic.
     * Because the dependencies of this class are all @Autowired, testing their absence
     * is not consistent with testing style; execution of the whole program will fail if they cannot be generated.
     * So unless we check for this everywhere, checking for nulls here is meaningless.
     * <p>
     * Testing:
     * 1) the returned string points to 'home'
     * 2) Verify 4 attributes have been added to Model; if design changes (ex. more attributes) test should change
     * 3) Verify addition of "versionNumber", "projectsList",  and "employeesListProjectsCnt" to Model
     * 4) Verify addition "projectStatusCnt" and that content of String jsonString conforms to JSON format
     */
    @Test
    void displayHome() throws JsonProcessingException {
        // 1)
        assertEquals("home", homeController.displayHome(mockModel));
        // 2)
        verify(mockModel, times(4)).addAttribute(any(String.class), any());
        // 3)
        verify(mockModel, times(1)).addAttribute(matches("versionNumber"), any());
        verify(mockModel, times(1)).addAttribute(matches("projectsList"), any());
        verify(mockModel, times(1)).addAttribute(matches("employeesListProjectsCnt"), any());
        // 4)
        verify(mockModel, times(1)).addAttribute(matches("projectStatusCnt"), matches("\\[\\{*.*\\}*\\]"));
    }
}
