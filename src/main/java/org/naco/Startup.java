package org.naco;

import com.sun.tools.javac.Main;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.naco.models.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class Startup {

    @Inject
    MainFacade mainFacade;

    public void init(@Observes StartupEvent startupEvent) {

        //User.add("admin", "admin", "admin");
        //User.add("user", "user", "user");

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        employee.setFio("Дылинов Николай Андреевич");
        employee.setPost(Post.WORKER);
        employee.setRank((short)1);
        mainFacade.addEmployee(employee);
        employees.add(employee);
        employee = new Employee();
        employee.setUsername("krasilnikov");
        employee.setFio("Красильников Анатолий Игоревич");
        employee.setPost(Post.CHIEF);
        employee.setRank((short)2);
        mainFacade.addEmployee(employee);
        employees.add(employee);
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setChief(employees.get(1));
        task.setContent("Одна из самых первых задач");
        task.setDate(new Date());
        task.setCompleted(true);
        mainFacade.addTask(task);
        tasks.add(task);
        task = new Task();
        task.setChief(employees.get(1));
        task.setContent("Вторая задачка");
        task.setDate(new Date());
        task.setCompleted(false);
        mainFacade.addTask(task);
        tasks.add(task);
        List<Perform> performs = new ArrayList<>();
        Perform perform = new Perform();
        perform.setWorker(employees.get(0));
        perform.setTask(tasks.get(0));
        mainFacade.addPerform(perform);
        performs.add(perform);
    }

}
