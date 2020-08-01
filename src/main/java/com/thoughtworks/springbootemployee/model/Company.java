package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "companyId")
    private List<Employee> employees;

    public Company() {
    }

    public Company(Integer id, String companyName, Integer employeesNumber, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }
//
//    public Company(String companyName, Integer employeesNumber, List<Employee> employees) {
//        this.companyName = companyName;
//        this.employeesNumber = employeesNumber;
//        this.employees = employees;
//    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
