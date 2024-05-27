package org.naco.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.naco.controllers.EmployeeController;
import org.naco.models.entities.Employee;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeController employeeController;

    public Employee getEmployeeByLogin(String login) {
        return employeeController.getEmployeeByUsername(login);
    }

}
