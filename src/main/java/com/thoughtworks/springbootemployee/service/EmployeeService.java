package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getAll() {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee: employees){
            employeeResponses.add(EmployeeMapper.convertEntityToEmployeeResponse(employee));
        }
        return employeeResponses;
    }

    public EmployeeResponse getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return EmployeeMapper.convertEntityToEmployeeResponse(employee);
    }

    public EmployeeResponse addEmployee(Employee employee) {
        Employee returnEmployee = employeeRepository.save(employee);
        return EmployeeMapper.convertEntityToEmployeeResponse(returnEmployee);
    }

    public EmployeeResponse updateEmployee(Integer employeeId, Employee employee) throws NotSuchDataException, IllegalOperationException {
        //todo employeeId
        if(!employeeId.equals(employee.getId())){
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
            //todo nosuch
            throw new NotSuchDataException();
        }
        return EmployeeMapper.convertEntityToEmployeeResponse(updatedEmployee);
    }

    public EmployeeResponse deleteEmployee(Integer id) throws NotSuchDataException {
        Employee deletedEmployee = employeeRepository.findById(id).orElse(null);
        if (deletedEmployee != null) {
            employeeRepository.delete(deletedEmployee);
        }else {
            throw new NotSuchDataException();
        }
        return EmployeeMapper.convertEntityToEmployeeResponse(deletedEmployee);
    }
    //todo convert response
    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<EmployeeResponse> getEmployeesByGender(String gender) {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        List<Employee> employees =employeeRepository.findByGender(gender);
        for(Employee employee : employees){
            employeeResponses.add(EmployeeMapper.convertEntityToEmployeeResponse(employee));
        }
        return employeeResponses;
    }
}
