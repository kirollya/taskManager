package org.naco.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.naco.controllers.EmployeeController;
import org.naco.models.entities.Employee;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeController employeeController;

    public Employee getEmployeeByLogin(String login) {
        return employeeController.getEmployeeByUsername(login);
    }

    @Transactional
    public void updEmployeeRank(Long id, Integer rank) {
        employeeController.getEmployeeRepository().update("rank = ?1 where id = ?2", rank, id);
    }
}
