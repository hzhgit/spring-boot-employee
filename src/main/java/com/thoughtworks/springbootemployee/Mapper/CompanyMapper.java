package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.beans.BeanUtils;

public class CompanyMapper {
    public static Company convertCompanyRequestToCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest,company);
        return company;
    }
}
