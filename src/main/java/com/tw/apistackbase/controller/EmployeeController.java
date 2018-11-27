package com.tw.apistackbase.controller;

import com.tw.apistackbase.service.Employee;
import com.tw.apistackbase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Employee>> getAll() {

        List<Employee> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Integer> create(@RequestBody Employee employee) {

        int id = employeeService.create(employee);
        return ResponseEntity.ok(id);
    }

    @PutMapping(path = "/{employeeId}", consumes = {"application/json"})
    public ResponseEntity update(@PathVariable int employeeId, @RequestBody Employee employee) {

        boolean isUpdated = employeeService.update(employeeId, employee);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity delete(@PathVariable int employeeId) {

        boolean isDeleted = employeeService.delete(employeeId);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
