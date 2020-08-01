package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Page<Company> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            return company.getEmployees();
        }
        return null;
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Integer companyID, Company company) throws NotSuchDataException, IllegalOperationException {
        if(companyID != company.getId()){
            throw new IllegalOperationException();
        }
        Company fetchedCompany = companyRepository.findById(companyID).orElse(null);
        if (fetchedCompany != null) {
            fetchedCompany.setCompanyName(company.getCompanyName());
            fetchedCompany.setEmployees(company.getEmployees());
            fetchedCompany.setEmployeesNumber(company.getEmployeesNumber());
            fetchedCompany = companyRepository.save(company);
        }else{
            throw new NotSuchDataException();
        }
        return fetchedCompany;
    }

    public Company deleteCompanyById(Integer companyId) throws NotSuchDataException {
        Company fetchedCompany = companyRepository.findById(companyId).orElse(null);
        if (fetchedCompany != null) {
            companyRepository.delete(fetchedCompany);
        }else {
            throw new NotSuchDataException();
        }
        return fetchedCompany;
    }
}
