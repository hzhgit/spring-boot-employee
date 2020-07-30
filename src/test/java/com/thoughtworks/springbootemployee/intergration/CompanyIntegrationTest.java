package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;

    private void initCompany(){
        Company company = new Company(1,"OOCL",1, Collections.singletonList(
                new Employee(1, "zach", 18, "male", 1000, 1)
        ));
        companyRepository.save(company);
    }

    @Test
    void should_return_companies__when_hit_get_all_companies_given_nothing() throws Exception {
        //given
        initCompany();

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees",hasSize(1)));
    }

    @Test
    void should_return_companies_when_hit_get_company_by_page_endpoint_given_page_and_page_size() throws Exception {
        //given
        initCompany();
        int page = 1;
        int pageSize = 1;

        //when
        mockMvc.perform(get("/companies?page=" + page + "&pageSize=" + pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$.content[0].employeesNumber").value(1))
                .andExpect(jsonPath("$.content[0].employees", hasSize(1)));
    }

    @Test
    void should_return_companies_when_hit_get_company_by_id_given_id() throws Exception {
        //given
        initCompany();

        //when
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees",hasSize(1)));
    }

    @Test
    void should_return_employees_when_hit_get_company_employees_by_id_given_company_id() throws Exception {
        //given
        initCompany();

        //when
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("zach"))
                .andExpect(jsonPath("$.employees[0].age").value(18))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(1000))
                .andExpect(jsonPath("$.employees[0].companyId").value(1));
    }
}
