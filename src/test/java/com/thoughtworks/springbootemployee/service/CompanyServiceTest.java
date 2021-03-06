package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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

public class CompanyServiceTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final CompanyService companyService = new CompanyService(companyRepository);

    @Test
    void should_return_companies_when_getAll_given_none() {
        //given
        given(companyRepository.findAll()).willReturn(Arrays.asList(
                new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                        new Employee(4, "alibaba1", 20, "male", 6000),
                        new Employee(11, "tengxun2", 19, "female", 7000),
                        new Employee(6, "alibaba3", 19, "male", 8000),
                        new Employee(13, "huawei", 60, "male", 4000),
                        new Employee(1, "Quentin", 18, "male", 10000),
                        new Employee(5, "goodboy", 70, "female", 5000)
                ))),
                new Company(2, "tx", 100, new ArrayList<>(Arrays.asList(
                        new Employee(4, "tx", 20, "male", 6000),
                        new Employee(5, "gd", 70, "remale", 5000)
                )))
        ));

        //when
        List<CompanyResponse> companies = companyService.getAll();
        //then
        assertNotNull(companies);
        assertEquals(2, companies.size());
    }

    @Test
    void should_return_company_when_getCompany_by_id_given_id_is_1() {
        //given
        Integer id = 1;
        given(companyRepository.findById(id)).willReturn(
                Optional.of(
                        new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                        new Employee(4, "alibaba1", 20, "male", 6000),
                        new Employee(11, "tengxun2", 19, "female", 7000),
                        new Employee(6, "alibaba3", 19, "male", 8000),
                        new Employee(13, "huawei", 60, "male", 4000),
                        new Employee(1, "Quentin", 18, "male", 10000),
                        new Employee(5, "goodboy", 70, "female", 5000)
                ))))
        );

        //when
        CompanyResponse company = companyService.getCompanyById(id);
        //then
        assertNotNull(company);
        assertEquals(id, company.getId());
    }

    @Test
    void should_return_companies_when_getAll_by_page_given_page_and_pageSize() {
        //given
        int page = 1;
        int pageSize = 1;
        given(companyRepository.findAll(PageRequest.of(page - 1, pageSize))).willReturn(Page.empty());

        //when
        Page<Company> companies = companyService.getCompaniesByPage(page, pageSize);
        //then
        assertNotNull(companies);
    }

    @Test
    void should_return_employees_when_getEmployees_by_company_id_given_company_id_is_1() {
        //given
        int companyId = 1;
        given(companyRepository.findById(companyId)).willReturn(
                Optional.of(
                        new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                        new Employee(4, "alibaba1", 20, "male", 6000),
                        new Employee(11, "tengxun2", 19, "female", 7000),
                        new Employee(6, "alibaba3", 19, "male", 8000),
                        new Employee(13, "huawei", 60, "male", 4000),
                        new Employee(1, "Quentin", 18, "male", 10000),
                        new Employee(5, "goodboy", 70, "female", 5000)
                ))))
        );

        //when
        List<EmployeeResponse> employees = companyService.getEmployeesByCompanyId(companyId);
        //then
        assertNotNull(employees);
        assertEquals(6, employees.size());
    }

    @Test
    void should_return_added_company_when_add_company_given_company() {
        //given
        Company company = new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                new Employee(4, "alibaba1", 20, "male", 6000),
                new Employee(11, "tengxun2", 19, "female", 7000),
                new Employee(6, "alibaba3", 19, "male", 8000),
                new Employee(13, "huawei", 60, "male", 4000),
                new Employee(1, "Quentin", 18, "male", 10000),
                new Employee(5, "goodboy", 70, "female", 5000)
        )));
        given(companyRepository.save(company)).willReturn(company);

        //when
        CompanyResponse addedCompany = companyService.addCompany(company);
        //then
        assertNotNull(addedCompany);
        assertEquals(company.getId(), addedCompany.getId());
    }

    @Test
    void should_return_updated_company_when_update_company_given_companyId_is_1() throws NoSuchDataException, IllegalOperationException {
        //given
        Integer companyId = 1;
        Company company = new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                new Employee(4, "alibaba1", 20, "male", 6000),
                new Employee(11, "tengxun2", 19, "female", 7000),
                new Employee(6, "alibaba3", 19, "male", 8000),
                new Employee(13, "huawei", 60, "male", 4000),
                new Employee(1, "Quentin", 18, "male", 10000),
                new Employee(5, "goodboy", 70, "female", 5000)
        )));
        given(companyRepository.findById(companyId)).willReturn(Optional.of(company));
        given(companyRepository.save(company)).willReturn(company);

        //when
        CompanyResponse updatedCompany = companyService.updateCompany(companyId, company);
        //then
        assertNotNull(updatedCompany);
        assertEquals(company.getId(), updatedCompany.getId());
        assertEquals(company.getCompanyName(), updatedCompany.getCompanyName());
        assertEquals(company.getEmployees(), updatedCompany.getEmployees());
        assertEquals(company.getEmployeesNumber(), updatedCompany.getEmployeesNumber());
    }

    @Test
    void should_return_deleted_company_when_delete_company_given_company_id_is_1() throws NoSuchDataException {
        //given
        Integer companyId = 1;
        Company company = new Company(1, "alibaba", 200, new ArrayList<>(Arrays.asList(
                new Employee(4, "alibaba1", 20, "male", 6000),
                new Employee(11, "tengxun2", 19, "female", 7000),
                new Employee(6, "alibaba3", 19, "male", 8000),
                new Employee(13, "huawei", 60, "male", 4000),
                new Employee(1, "Quentin", 18, "male", 10000),
                new Employee(5, "goodboy", 70, "female", 5000)
        )));
        given(companyRepository.findById(companyId)).willReturn(Optional.of(company));

        //when
        CompanyResponse deletedCompany = companyService.deleteCompanyById(companyId);
        //then
        assertNotNull(deletedCompany);
        assertEquals(company.getId(), deletedCompany.getId());
    }

    @Test
    void should_return_not_such_data_when_delete_company_given_wrong_id() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        Throwable exception = assertThrows(NoSuchDataException.class,
                () -> companyService.deleteCompanyById(1));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_return_not_such_data_when_updat_company_given_id_not_exists() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        Company company = new Company();
        company.setId(1);
        //when
        Throwable exception = assertThrows(NoSuchDataException.class,
                () -> companyService.updateCompany(1, company));
        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
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
