package org.naco;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import org.naco.controllers.EmployeeController;
import org.naco.controllers.PerformController;
import org.naco.controllers.TaskController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Post;
import org.naco.models.entities.Task;
import org.naco.services.EmployeeService;
import org.naco.services.MyTasksService;

import java.util.List;

@ApplicationScoped
public class MainFacade {

    @Inject
    EmployeeController employeeController;

    @Inject
    TaskController taskController;

    @Inject
    PerformController performController;

    @Inject
    MyTasksService myTasksService;

    @Inject
    EmployeeService employeeService;

    public Employee addEmployee(Employee employee) {
        try {
            employee.setPassword(BcryptUtil.bcryptHash(employee.getUsername()));
            employee.setRole(employee.getPost() == Post.WORKER ? "user" : "admin");
            employeeController.addEmployee(employee);
        } catch (Exception e) {
            return null;
        }
        return employee;
    }

    public boolean deleteEmployee(Long id) {
        try {
            employeeController.deleteEmployee(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Task addTask(Task task) {
        try {
            taskController.addTask(task);
        } catch (Exception e) {
            return null;
        }
        return task;
    }

    public Perform addPerform(Perform perform) {
        try {
            performController.addPerform(perform);
        } catch (Exception e) {
            return null;
        }
        return perform;
    }

    public List<Employee> getAllEmployees() {
        return employeeController.getEmployeeRepository().listAll();
    }

    public List<Task> getAllTasks() {
        return taskController.getTaskRepository().listAll();
    }

    public List<Perform> getAllPerforms() {
        return performController.getPerformRepository().listAll();
    }

    public int authenticate(String login, String password) {
        return 0;
    }

    public Employee getEmployee(Long id) {
        return employeeController.getEmployeeRepository().findById(id);
    }

    public List<Task> getTasksAsChief(Employee employee) {
        return myTasksService.getTasksAsChief(employee);
    }

    public List<Task> getTasksAsWorker(Employee employee) {
        return myTasksService.getTasksAsWorker(employee);
    }

    public Employee getEmployeeByLogin(String login) {
        return employeeService.getEmployeeByLogin(login);
    }

    public boolean deleteTask(Long id) {
        return myTasksService.deleteTaskById(id);
    }

    public void updTaskCompleted(Long id, Boolean completed) {
        myTasksService.updTaskCompleted(id, completed);
    }
}
