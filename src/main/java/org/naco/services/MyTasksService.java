package org.naco.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.naco.controllers.EmployeeController;
import org.naco.controllers.PerformController;
import org.naco.controllers.TaskController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Task;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MyTasksService {

    @Inject
    EmployeeController employeeController;

    @Inject
    TaskController taskController;

    @Inject
    PerformController performController;

    public List<Task> getTasksAsChief(Employee employee) {
        return taskController.getTaskRepository().list("chief", employee);
    }

    public List<Task> getTasksAsWorker(Employee employee) {
        List<Perform> performs = performController.getPerformRepository().list("worker", employee);
        List<Task> tasks = new ArrayList<>();
        for (Perform perform: performs) {
            tasks.add(perform.getTask());
        }
        return tasks;
    }

    @Transactional
    public Boolean deleteTaskById(Long id) {
        try {
            performController.deleteAllWithTask(taskController.getTaskRepository().findById(id));
            taskController.deleteTask(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
