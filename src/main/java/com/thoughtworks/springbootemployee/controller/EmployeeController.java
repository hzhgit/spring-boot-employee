package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping()
    public List<EmployeeResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Employee> getAllByPage(Integer page, Integer pageSize) {
        return service.getEmployeesByPage(page, pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getAllByGender(String gender) {
        return service.getEmployeesByGender(gender);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer employeeId) {
        return service.getEmployeeById(employeeId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody Employee employee) {
        return service.addEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse modifyEmployee(@RequestBody Employee modifiedEmployee, @PathVariable Integer employeeId) throws NotSuchDataException, IllegalOperationException {
        return service.updateEmployee(employeeId, modifiedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public EmployeeResponse deleteEmployee(@PathVariable Integer employeeId) throws NotSuchDataException {
        return service.deleteEmployee(employeeId);
    }

}
