package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.EmployeeDto;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Test
    void should_return_employee_when_mapper_given_employee_request() {
        //given
        EmployeeDto employeeDto = new EmployeeDto(1,"zach",18,"male",1000,1);;
        //when
        Employee employee = EmployeeMapper.convertDtoToEntity(employeeDto);
        //then
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getAge(), employeeDto.getAge());
        assertEquals(employee.getName(), employeeDto.getName());
        assertEquals(employee.getGender(), employeeDto.getGender());
        assertEquals(employee.getCompanyId(), employeeDto.getCompanyId());
    }

    @Test
    void should_return_employee_response_when_mapper_given_employee() {
        //given
        Employee employee = new Employee(1,"zach",18,"male",1000,1);
        //when
        EmployeeDto employeeDto = EmployeeMapper.convertEntityToDto(employee);
        //then
        assertEquals(employee.getId(),employeeDto.getId());
        assertEquals(employee.getAge(), employeeDto.getAge());
        assertEquals(employee.getName(), employeeDto.getName());
        assertEquals(employee.getGender(), employeeDto.getGender());
        assertEquals(employee.getCompanyId(), employeeDto.getCompanyId());
    }
}
