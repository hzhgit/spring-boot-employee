package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
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
    public List<CompanyResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Company> getAllByPage(Integer page, Integer pageSize) {
        return service.getCompaniesByPage(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyByCompanyId(@PathVariable Integer companyId) {
        return service.getCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getAllEmployeesByCompanyId(@PathVariable Integer companyId) {
        return service.getEmployeesByCompanyId(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = CompanyMapper.convertCompanyRequestToCompany(companyRequest);
        return service.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompanyById(@RequestBody CompanyRequest companyRequest, @PathVariable Integer companyId) throws NoSuchDataException, IllegalOperationException {
        Company company = CompanyMapper.convertCompanyRequestToCompany(companyRequest);
        return service.updateCompany(companyId, company);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CompanyResponse deleteCompanyById(@PathVariable Integer companyId) throws NoSuchDataException {
        return service.deleteCompanyById(companyId);
    }

}
