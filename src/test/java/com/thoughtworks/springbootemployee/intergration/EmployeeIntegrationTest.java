package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private final Company testCompany = new Company(1, "OOCL", 6, Collections.emptyList());

    private final List<Employee> testEmployees = Arrays.asList(
            new Employee(1, "zach", 21, "male", 5000, 1),
            new Employee(2, "york", 22, "female", 6000, 1),
            new Employee(3, "alex", 23, "female", 7000, 1),
            new Employee(4, "green", 24, "male", 8000, 1),
            new Employee(5, "karen", 25, "male", 9000, 1),
            new Employee(6, "chris", 26, "male", 9000, 1)
    );

    @BeforeEach
    private void initData() {
        companyRepository.save(testCompany);
    }

    @AfterEach
    private void deleteDate() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_employees_when_hit_get_employee_endpoint_given_nothing() throws Exception {
        //given
        Employee employee = testEmployees.get(0);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee.getCompanyId()));
    }

    @Test
    void should_return_employees_when_hit_get_employee_by_page_endpoint_given_page_and_page_size() throws Exception {
        //given
        employeeRepository.saveAll(testEmployees);
        int page = 1;
        int pageSize = 3;

        //when
        mockMvc.perform(get("/employees?page=" + page + "&pageSize=" + pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(pageSize)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalElements").value(6));
    }

    @Test
    void should_return_employee_when_hit_get_employee_by_gender_endpoint_given_gender() throws Exception {
        //given
        employeeRepository.saveAll(testEmployees);
        String gender = "male";

        //when
        mockMvc.perform(get("/employees?gender=" + gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[2].gender").value("male"))
                .andExpect(jsonPath("$[3].gender").value("male"));
    }

    @Test
    void should_return_employee_when_hit_get_employee_by_id_given_id() throws Exception {
        //given
        Employee employee = employeeRepository.save(testEmployees.get(0));

        //when
        mockMvc.perform(get("/employees?id=" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee.getCompanyId()));
    }

    @Test
    void should_create_employee_when_hit_add_employee_given_employee() throws Exception {
        //given
        String employeeInfo = "{\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"hzh\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 3000,\n" +
                "                \"companyId\": 1\n" +
                "            }";
        //when
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(employeeInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("hzh"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(3000))
                .andExpect(jsonPath("$.companyId").value(1));
    }

    @Test
    void should_update_employee_when_hit_update_employee_given_new_employee() throws Exception {
        //given
        String newEmployee = "{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Zach\",\n" +
                "                \"age\": 23,\n" +
                "                \"gender\": \"female\",\n" +
                "                \"salary\": 3000,\n" +
                "                \"companyId\": 1\n" +
                "            }";
        //when
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Zach"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(3000))
                .andExpect(jsonPath("$.companyId").value(1));
    }

    @Test
    void should_delete_employee_when_hit_delete_employee_endpoint_given_id() throws Exception {
        //given
        //when
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("ShaoLi"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(500))
                .andExpect(jsonPath("$.companyId").value(1));
    }
}
