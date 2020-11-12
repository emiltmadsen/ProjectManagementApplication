package com.example.demo.dto;

public interface EmployeeProject {

    // Need to have the property names begin with 'get' for Spring Data to be able to read/use it
    public String getFirstName();
    public String getLastName();
    public int getProjectCount();

}
