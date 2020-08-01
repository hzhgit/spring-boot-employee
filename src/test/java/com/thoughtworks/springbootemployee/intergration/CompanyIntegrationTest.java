package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;

    private final List<Company> testCompanies = Arrays.asList(
            new Company(1, "OOCL", 1, Collections.singletonList(
                    new Employee(1, "zach", 20, "male", 5000, 1))),
            new Company(2, "TW", 1, Collections.singletonList(
                    new Employee(2, "woody", 22, "male", 6000, 2))),
            new Company(3, "Alibaba", 1, Collections.singletonList(
                    new Employee(3, "hzh", 22, "male", 7000, 3)))
    );

    @AfterEach
    private void afterAll() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies__when_hit_get_all_companies_given_nothing() throws Exception {
        //given
        Company company = companyRepository.save(testCompanies.get(0));

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1));
    }

    @Test
    void should_return_companies_when_hit_get_company_by_page_endpoint_given_page_and_page_size() throws Exception {
        //given
        companyRepository.saveAll(testCompanies);
        int page = 1;
        int pageSize = 1;

        //when
        mockMvc.perform(get("/companies?page=" + page + "&pageSize=" + pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(pageSize)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void should_return_companies_when_hit_get_company_by_id_given_id() throws Exception {
        //given
        Company company = companyRepository.save(testCompanies.get(0));

        //when
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees", hasSize(1)));
    }

    @Test
    void should_return_employees_when_hit_get_company_employees_by_id_given_company_id() throws Exception {
        //given
        Company company = companyRepository.save(testCompanies.get(0));

        //when
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("zach"))
                .andExpect(jsonPath("$.employees[0].age").value(20))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(5000))
                .andExpect(jsonPath("$.employees[0].companyId").isNumber());
    }

    @Test
    void should_return_company_when_hit_add_company_endpoint_given_company() throws Exception {
        //given
        String comanyInfo = "{\n" +
                "    \"id\": 2,\n" +
                "    \"companyName\": \"blibli\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"age\": 18,\n" +
                "            \"name\": \"zach\",\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 1000.0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"employeesNumber\": 1\n" +
                "}";
        //then
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(comanyInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("blibli"))
                .andExpect(jsonPath("$.employeesNumber").value(1));
    }

    @Test
    void should_return_company_when_hit_update_company_endpoint_given_new_company() throws Exception {
        //given
        Employee employee = new Employee(null, "hzh", 16, "male", 1000);
        Company company = new Company(null, "hzhCompany", null, Arrays.asList(employee));
        Company saveCompany = companyRepository.save(company);
        String comanyInfo = "{\n" +
                "    \"id\": " + saveCompany.getId() + ",\n" +
                "    \"companyName\": \"TW\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": " + employee.getId() + ",\n" +
                "            \"age\": 20,\n" +
                "            \"name\": \"hzh\",\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 2000.0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"employeesNumber\": 1\n" +
                "}";
        //then
        mockMvc.perform(put("/companies/" + company.getId()).contentType(MediaType.APPLICATION_JSON).content(comanyInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("TW"))
                .andExpect(jsonPath("$.employeesNumber").value(1));
    }

    @Test
    void should_delete_company_when_hit_delete_company_endpoint_given_id() throws Exception {
        //given
        Company company = companyRepository.save(testCompanies.get(0));

        //then
        mockMvc.perform(delete("/companies/" + company.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("zach"))
                .andExpect(jsonPath("$.employees[0].age").value(20))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(5000));
    }
}
