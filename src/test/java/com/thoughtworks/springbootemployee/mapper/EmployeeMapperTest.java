package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Test
    void should_return_employee_when_mapper_given_employee_request() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest(1,"zach",18,"male",1000,1);;
        //when
        Employee employee = EmployeeMapper.map(employeeRequest);
        //then
        assertEquals(employee.getId(),employeeRequest.getId());
        assertEquals(employee.getAge(), employeeRequest.getAge());
        assertEquals(employee.getName(), employeeRequest.getName());
        assertEquals(employee.getGender(), employeeRequest.getGender());
        assertEquals(employee.getCompanyId(), employeeRequest.getCompanyId());
    }
}
