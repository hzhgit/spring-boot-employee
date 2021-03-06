package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeService service = new EmployeeService(employeeRepository);

    @Test
    void should_return_all_employees_when_get_all_given_none() {
        //given
        given(employeeRepository.findAll()).willReturn(new ArrayList<>(Arrays.asList(
                new Employee(4, "alibaba1", 20, "male", 6000),
                new Employee(1, "Quentin", 18, "male", 10000)
        )));

        //when
        List<EmployeeResponse> employees = service.getAll();

        //then
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employee_when_get_by_id_given_id() {
        //given
        Integer id = 1;
        given(employeeRepository.findById(id))
                .willReturn(Optional.of(new Employee(1, "Quentin", 18, "male", 10000)));

        //when
        EmployeeResponse employee = service.getEmployeeById(id);

        //then
        assertNotNull(employee);
        assertEquals(id, employee.getId());
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        Employee employee = new Employee(80, "ggggggg", 20, "male", 100);
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeResponse addedEmployee = service.addEmployee(employee);

        //then
        assertNotNull(addedEmployee);
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_and_employeeId_is_1() throws NoSuchDataException, IllegalOperationException {
        //given
        int employeeId = 1;
        Employee employee = new Employee(1, "HHHHHHH", 30, "female", 200);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeResponse updatedEmployee = service.updateEmployee(employeeId, employee);

        //then
        assertNotNull(updatedEmployee);
        assertEquals(employee.getId(), updatedEmployee.getId());
        assertEquals(employee.getName(), updatedEmployee.getName());
        assertEquals(employee.getAge(), updatedEmployee.getAge());
        assertEquals(employee.getGender(), updatedEmployee.getGender());
        assertEquals(employee.getSalary(), updatedEmployee.getSalary());
    }

    @Test
    void should_return_deleted_employee_when_delete_given_id() throws NoSuchDataException {
        //given
        Integer id = 1;
        given(employeeRepository.findById(id)).willReturn(
                Optional.of(new Employee(1, "Quentin", 18, "male", 10000)));

        //when
        EmployeeResponse employee = service.deleteEmployee(id);

        //then
        assertNotNull(employee);
        assertEquals(id, employee.getId());
    }

    @Test
    void should_return_employees_when_query_by_page_given_page_and_pageSize() {
        //given
        int page = 1;
        int pageSize = 5;
        given(employeeRepository.findAll(PageRequest.of(page - 1, pageSize))).willReturn(Page.empty());

        //when
        Page<Employee> employees = service.getEmployeesByPage(page, pageSize);

        //then
        assertNotNull(employees);
    }

    @Test
    void should_return_employees_when_query_by_gender_given_gender_is_male() {
        //given
        String gender = "male";
        given(employeeRepository.findByGender(gender)).willReturn(Arrays.asList(
                new Employee(1, "Quentin", 18, "male", 10000),
                new Employee(2, "Quentin", 18, "male", 10000),
                new Employee(3, "Quentin", 18, "male", 10000),
                new Employee(4, "Quentin", 18, "male", 10000),
                new Employee(5, "Quentin", 18, "male", 10000)
        ));
        //when
        List<EmployeeResponse> employees = service.getEmployeesByGender(gender);

        //then
        assertNotNull(employees);
        assertEquals(5, employees.size());
    }

    @Test
    void should_return_not_such_data_when_delete_employee_given_wrong_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        //when
        Throwable exception = assertThrows(NoSuchDataException.class,
                () -> employeeService.deleteEmployee(1));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_not_such_data_when_update_given_id_not_exists() {
        //given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Employee employee = new Employee();
        employee.setId(1);
        //when
        Throwable exception = assertThrows(NoSuchDataException.class,
                () -> employeeService.updateEmployee(1, employee));
        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_illegal_operation_exception_when_update_given_id_not_equals_updated_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        int id = 1;
        Employee employee = new Employee(2, "zach", 18, "male", 1000);

        //when
        Throwable exception = assertThrows(IllegalOperationException.class,
                () -> employeeService.updateEmployee(1, employee));

        //then
        assertEquals(IllegalOperationException.class,exception.getClass());
    }
}
