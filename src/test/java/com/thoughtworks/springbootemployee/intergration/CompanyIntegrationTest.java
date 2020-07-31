package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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

    @Test
    void should_return_company_when_hit_add_company_endpoint_given_company() throws Exception {
        //given
        initCompany();
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
        initCompany();
        String comanyInfo = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"TW\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"age\": 20,\n" +
                "            \"name\": \"hzh\",\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 2000.0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"employeesNumber\": 1\n" +
                "}";
        //then
        mockMvc.perform(put("/companies/1").contentType(MediaType.APPLICATION_JSON).content(comanyInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("TW"))
                .andExpect(jsonPath("$.employeesNumber").value(1));
    }

    @Test
    void should_delete_company_when_hit_delete_company_endpoint_given_id() throws Exception {
        //given
        initCompany();

        //then
        mockMvc.perform(delete("/companies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("zach"))
                .andExpect(jsonPath("$.employees[0].age").value(18))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(1000))
                .andExpect(jsonPath("$.employees[0].companyId").value(1));
    }
}
