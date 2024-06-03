package org.naco.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.naco.controllers.EmployeeController;
import org.naco.controllers.PerformController;
import org.naco.controllers.TaskController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Post;
import org.naco.models.entities.Task;

import java.util.ArrayList;
import java.util.Date;
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

    @Transactional
    public void updTaskCompleted(Long id, Boolean completed) {
        taskController.getTaskRepository().update("completed = ?1 where id = ?2", completed, id);
    }

    @Transactional
    public void updTaskDate(Long id, Date date) {
        taskController.getTaskRepository().update("date = ?1 where id = ?2", date, id);
    }

    public Task getTaskById(Long id) {
        return taskController.getTaskRepository().findById(id);
    }

    public List<Employee> getWorkersForTask(Task task) {
        List<Perform> performs = performController.getPerformRepository().list("task = ?1", task);
        return performs.stream().map(Perform::getWorker).toList();
    }

    public List<Task> getTasksByParams(Long workerId, Long chiefId, Date date) {
        List<Task> result = null;
        /*if (workerId == null) {
            if (date == null)
                return taskController.getTaskRepository().listAll();
            return  taskController.getTaskRepository().list("date <= ?1", date);
        }
        Employee employee = employeeController.getEmployeeRepository().findById(workerId);
        List<Perform> performs = performController.getPerformRepository().list("worker", employee);
        if (date != null)
            return performs.stream().map(Perform::getTask).filter(task -> task.getDate().before(date) || task.getDate().equals(date)).toList();
        return performs.stream().map(Perform::getTask).toList();*/
        if (workerId != null) {
            result = new ArrayList<>();
            Employee employee = employeeController.getEmployeeRepository().findById(workerId);
            List<Perform> performs = performController.getPerformRepository().list("worker", employee);
            result = performs.stream().map(Perform::getTask).toList();
        }
        if (chiefId != null) {
            if (result != null) {
                result = result.stream().filter(task -> task.getChief().getId().equals(chiefId)).toList();
            } else {
                result = new ArrayList<>();
                Employee employee = employeeController.getEmployeeRepository().findById(chiefId);
                result = taskController.getTaskRepository().list("chief", employee);
            }
        }
        if (date != null) {
            if (result != null) {
                result = result.stream().filter(task -> task.getDate().before(date) || task.getDate().equals(date)).toList();
            } else {
                result = new ArrayList<>();
                result = taskController.getTaskRepository().list("date <= ?1", date);
            }
        }
        if (result != null)
            return result;
        return taskController.getTaskRepository().listAll();
    }

    public List<Employee> getAllChiefs() {
        return employeeController.getEmployeeRepository().list("post!=?1", Post.WORKER);
    }

    public List<Employee> getEmployeesForChief(Employee me) {
        List<Employee> workers = employeeController.getEmployeeRepository().listAll();
        workers = workers.stream()
                .filter(employee -> employee.getPost().equals(Post.WORKER)
                        || (employee.getRank() < me.getRank()))
                .toList();
        return workers;
    }

    @Transactional
    public void assignWorker(Long task_id, Long worker_id) {
        Employee worker = employeeController.getEmployeeRepository().findById(worker_id);
        Task task = taskController.getTaskRepository().findById(task_id);
        Perform perform = new Perform();
        perform.setWorker(worker);
        perform.setTask(task);
        performController.addPerform(perform);
    }
}
