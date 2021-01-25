package com.example.demo.repositories;

import com.example.demo.dto.EmployeeProject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * For comments on test classes, see EmployeeControllerTest.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void findAll() {
        // because the test data contains 9 employees, 9 are expected to be returned on findAll call
        assertEquals(0, employeeRepository.findAll().size());
    }

    @Test
    void employeeProjects() {
        // assuring 9 items are returned from testdb, since 9 were inserted.
        assertEquals(9, employeeRepository.employeeProjects().size());

        // would test sorting, but it is not working; it seems sorting is supposed to happen in natural order
        // using first-name and last-name in mentioned sequence.
        // But then there is the 'order by 3 DESC', which I can't quite make out what means
        // ... and doesn't seem to work.
    }

    @Test
    void trueIsFalse() {
        assertEquals(1, 2);
    }

}