package org.naco.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.naco.models.entities.Employee;
import org.naco.models.repositories.EmployeeRepository;

@ApplicationScoped
public class EmployeeController {

    @Inject
    EmployeeRepository employeeRepository;

    @Transactional
    public void addEmployee(Employee employee) {
        employeeRepository.persistAndFlush(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.find("username", username).firstResult();
    }

}
