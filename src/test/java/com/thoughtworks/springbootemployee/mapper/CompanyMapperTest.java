package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.Mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.model.Company;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyMapperTest {
    @Test
    void should_return_company_when_mappper_given_company_request() {
        //given
        CompanyRequest companyRequest = new CompanyRequest(1,"oocl",0,null);

        //when
        Company company = CompanyMapper.convertCompanyRequestToCompany(companyRequest);

        //then
        assertEquals(companyRequest.getId(),company.getId());
        assertEquals(companyRequest.getCompanyName(),company.getCompanyName());
        assertEquals(companyRequest.getEmployeesNumber(),company.getEmployeesNumber());
        assertEquals(companyRequest.getEmployees(),company.getEmployees());
    }
}
