package com.thoughtworks.springbootemployee.exception;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class NotSuchDataExceptionTest {
    @Test
    void should_return_not_such_data_when_delete_employee_given_wrong_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        //when
        Throwable exception = assertThrows(NotSuchDataException.class,
                () -> employeeService.deleteEmployee(1));

        //then
        assertEquals(NotSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_not_such_data_when_update_given_id_not_exists() {
        //given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Employee employee = new Employee();
        employee.setId(1);
        //when
        Throwable exception = assertThrows(NotSuchDataException.class,
                () -> employeeService.updateEmployee(1, employee));
        //then
        assertEquals(NotSuchDataException.class, exception.getClass());
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
        assertEquals(IllegalOperationException.class,exception);
    }
}
