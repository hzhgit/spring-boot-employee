package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyResponse> getAll() {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            companyResponses.add(CompanyMapper.convertCompanyToCompanyResponse(company));
        }
        return companyResponses;
    }

    public CompanyResponse getCompanyById(Integer id) {
        CompanyResponse companyResponse = new CompanyResponse();
        Company company = companyRepository.findById(id).orElse(null);
        return companyResponse = CompanyMapper.convertCompanyToCompanyResponse(company);
    }

    public Page<Company> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<EmployeeResponse> getEmployeesByCompanyId(int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            List<EmployeeResponse> employeeResponses = new ArrayList<>();
            List<Employee> employees = company.getEmployees();
            for (Employee employee : employees) {
                employeeResponses.add(EmployeeMapper.convertEntityToEmployeeResponse(employee));
            }
            return employeeResponses;
        }
        return null;
    }

    public CompanyResponse addCompany(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        return companyResponse = CompanyMapper.convertCompanyToCompanyResponse(company);
    }

    public CompanyResponse updateCompany(Integer companyID, Company company) throws NotSuchDataException, IllegalOperationException {
        if (companyID != company.getId()) {
            throw new IllegalOperationException();
        }
        Company fetchedCompany = companyRepository.findById(companyID).orElse(null);
        if (fetchedCompany != null) {
            fetchedCompany.setCompanyName(company.getCompanyName());
            fetchedCompany.setEmployees(company.getEmployees());
            fetchedCompany.setEmployeesNumber(company.getEmployeesNumber());
            fetchedCompany = companyRepository.save(company);
        } else {
            throw new NotSuchDataException();
        }
        return CompanyMapper.convertCompanyToCompanyResponse(fetchedCompany);
    }

    public CompanyResponse deleteCompanyById(Integer companyId) throws NotSuchDataException {
        Company fetchedCompany = companyRepository.findById(companyId).orElse(null);
        if (fetchedCompany != null) {
            companyRepository.delete(fetchedCompany);
        } else {
            throw new NotSuchDataException();
        }
        return CompanyMapper.convertCompanyToCompanyResponse(fetchedCompany);
    }
}
