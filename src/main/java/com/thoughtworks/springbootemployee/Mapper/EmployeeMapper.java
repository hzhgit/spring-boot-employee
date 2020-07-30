package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeDto;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {

    public static Employee convertDtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
        return employee;
    }

    public static EmployeeDto convertEntityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
}
