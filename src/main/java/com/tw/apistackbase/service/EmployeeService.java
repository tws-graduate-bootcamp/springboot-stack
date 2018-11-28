package com.tw.apistackbase.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    private List<Employee> employees;
    private int idIndex = 0;

    public EmployeeService() {
        this.employees = new ArrayList<>();
        save(new Employee("testUser", 28, "male"));
        save(new Employee("testUser-2", 38, "female"));
        save(new Employee("testUser-3", 25, "female"));
        save(new Employee("testUser-4", 20, "male"));

    }

    public List<Employee> getAll() {

        return this.employees;
    }

    public Optional<Employee> findOne(int id) {

        return findEmployeeById(id);
    }

    public int save(Employee employee) {
        this.idIndex = this.idIndex + 1;
        employee.setId(idIndex);
        this.employees.add(employee);
        return idIndex;
    }

    public boolean update(int employeeId, Employee updatedEmployee) {
        boolean isSuccess = false;
        Optional<Employee> optionalEmployee = findEmployeeById(employeeId);
        if (optionalEmployee.isPresent()) {
            this.employees.remove(optionalEmployee.get());
            updatedEmployee.setId(employeeId);
            this.employees.add(updatedEmployee);
            isSuccess = true;
        }
        return isSuccess;
    }

    private Optional<Employee> findEmployeeById(int employeeId) {
        for (Employee employeeElement : this.employees) {
            if (employeeElement.getId() == employeeId) {
                return Optional.of(employeeElement);
            }
        }
        return Optional.empty();
    }

    public boolean delete(int employeeId) {
        boolean isSuccess = false;
        Optional<Employee> employee = findEmployeeById(employeeId);
        if (employee.isPresent()) {
            this.employees.remove(employee.get());
            isSuccess = true;
        }
        return isSuccess;
    }

    public List<Employee> getPage(Integer pageIndex, Integer pageSize) {
        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = pageIndex * pageSize;

        if (this.employees.size() < endIndex) {
            return new ArrayList<>();
        }

        return this.employees.subList(startIndex, endIndex);
    }

}
