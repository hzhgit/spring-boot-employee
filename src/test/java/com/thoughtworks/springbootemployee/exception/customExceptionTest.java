package com.thoughtworks.springbootemployee.exception;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
//todo ??servertest
public class customExceptionTest {
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
        assertEquals(IllegalOperationException.class,exception.getClass());
    }

    @Test
    void should_return_not_such_data_when_delete_company_given_wrong_id() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        Throwable exception = assertThrows(NotSuchDataException.class,
                () -> companyService.deleteCompanyById(1));

        //then
        assertEquals(NotSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_not_such_data_when_updat_company_given_id_not_exists() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        Company company = new Company();
        company.setId(1);
        //when
        Throwable exception = assertThrows(NotSuchDataException.class,
                () -> companyService.updateCompany(1, company));
        //then
        assertEquals(NotSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_illegal_operation_exception_when_update_company_given_id_not_equals_updated_employee_id() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        int id = 1;
        Company company = new Company(2,"OOCL",0,null);

        //when
        Throwable exception = assertThrows(IllegalOperationException.class,
                () -> companyService.updateCompany(1, company));

        //then
        assertEquals(IllegalOperationException.class,exception.getClass());
    }
}
