package com.tw.apistackbase.controller;

import com.tw.apistackbase.service.Employee;
import com.tw.apistackbase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Employee>> getPage(
            @RequestParam(name = "page", required = false) Integer pageIndex,
            @RequestParam(name = "size", required = false) Integer pageSize) {

        if (pageIndex == null || pageSize == null) {
            return  ResponseEntity.ok(employeeService.getAll());
        }
        return ResponseEntity.ok(employeeService.getPage(pageIndex, pageSize));
    }

    @GetMapping(path = "/{employeeId}", produces = {"application/json"})
    public ResponseEntity<Employee> findOne(@PathVariable int employeeId) {

        Optional<Employee> employee = employeeService.findOne(employeeId);
        return employee.isPresent() ?
                ResponseEntity.ok(employee.get()) :
                ResponseEntity.notFound().build();
    }


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Integer> create(@RequestBody Employee employee) {

        int id = employeeService.save(employee);
        return ResponseEntity.ok(id);
    }

    @PutMapping(path = "/{employeeId}", consumes = {"application/json"})
    public ResponseEntity update(@PathVariable int employeeId, @RequestBody Employee employee) {

        boolean isUpdated = employeeService.update(employeeId, employee);
        return isUpdated ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity delete(@PathVariable int employeeId) {

        boolean isDeleted = employeeService.delete(employeeId);
        return isDeleted ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
