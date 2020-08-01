package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {

    public static Employee convertEmployeeRequestToEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest,employee);
        return employee;
    }

    public static EmployeeResponse convertEntityToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee,employeeResponse);
        return employeeResponse;
    }
}
