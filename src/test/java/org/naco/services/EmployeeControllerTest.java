package org.naco.services;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.naco.controllers.EmployeeController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Post;

public class EmployeeControllerTest {

    @Inject
    EmployeeController employeeController;

    @Test
    public void getEmployee() {
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        employee.setFio("Дылинов Николай Андреевич");
        employee.setPost(Post.WORKER);
        employee.setRank((short)1);
        employeeController.addEmployee(employee);
        Assertions.assertEquals(employee, employeeController.getEmployeeByUsername("dylinov"));
    }

    @Test
    public void updEmployeeRank() {
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        employee.setFio("Дылинов Николай Андреевич");
        employee.setPost(Post.WORKER);
        employee.setRank((short)1);
        employeeController.addEmployee(employee);
        Assertions.assertEquals(employee, employeeController.getEmployeeRepository().findById(Long.parseLong("1")));
        employeeController.getEmployeeRepository().update("rank = ?1 where id = ?2", 3, 1);
        Assertions.assertNotEquals(employee, employeeController.getEmployeeRepository().findById(Long.parseLong("1")));
    }

}
