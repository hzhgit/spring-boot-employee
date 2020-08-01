package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Company> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Company> getAllByPage(Integer page, Integer pageSize) {
        return service.getCompaniesByPage(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyByCompanyId(@PathVariable Integer companyId) {
        return service.getCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer companyId) {
        return service.getEmployeesByCompanyId(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return service.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompanyById(@RequestBody Company company, @PathVariable Integer companyId) {
        return service.updateCompany(companyId, company);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Company deleteCompanyById(@PathVariable Integer companyId) throws IllegalOperationException {
        return service.deleteCompanyById(companyId);
    }

}
