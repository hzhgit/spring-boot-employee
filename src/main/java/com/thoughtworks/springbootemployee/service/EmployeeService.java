package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee employee) throws NotSuchDataException, IllegalOperationException {
        if(employee.getId() != employeeId){
            throw new IllegalOperationException();
        }
        Employee updatedEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (updatedEmployee != null) {

            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAge(employee.getAge());
            updatedEmployee.setGender(employee.getGender());
            updatedEmployee.setSalary(employee.getSalary());
            updatedEmployee = employeeRepository.save(updatedEmployee);
        }else {
            throw new NotSuchDataException();
        }
        return updatedEmployee;
    }

    public Employee deleteEmployee(Integer id) throws NotSuchDataException {
        Employee deletedEmployee = employeeRepository.findById(id).orElse(null);
        if (deletedEmployee != null) {
            employeeRepository.delete(deletedEmployee);
        }else {
            throw new NotSuchDataException();
        }
        return deletedEmployee;
    }

    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }
}
