package org.naco.tests;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.naco.MainFacade;
import org.naco.controllers.EmployeeController;
import org.naco.controllers.PerformController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Post;
import org.naco.models.entities.Task;
import org.naco.models.repositories.EmployeeRepository;
import org.naco.models.repositories.PerformRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
public class ServiceTest {
    MainFacade mainFacade;

    EmployeeRepository employeeRepository;
    PerformRepository performRepository;
    EmployeeController employeeController;
    PerformController performController;

    @Before
    public void setUp() {
        mainFacade = new MainFacade();
        employeeRepository = mock(EmployeeRepository.class);
        employeeController = new EmployeeController(employeeRepository);
        performRepository = mock(PerformRepository.class);
        performController = new PerformController(performRepository);
    }

    @Test
    public void testEmployeeFio() {
        Employee employee = new Employee();
        employee.setFio("Дылинов Николай Андреевич");
        assertEquals("Дылинов Николай Андреевич", employee.getFio());
    }

    @Test
    public void testEmployeeRank() {
        Employee employee = new Employee();
        employee.setPost(Post.WORKER);
        assertEquals(Post.WORKER, employee.getPost());
    }

    @Test
    public void testTaskCompleted() {
        Task task = new Task();
        task.setCompleted(false);
        assertEquals(false, task.isCompleted());
    }

    @Test
    public void testTaskContent() {
        Task task = new Task();
        task.setContent("Content");
        assertEquals("Content", task.getContent());
    }

    @Test
    public void testPerformWorker() {
        Employee employee = new Employee();
        employee.setFio("Красильников Анатолий Игоревич");
        Perform perform = new Perform();
        perform.setWorker(employee);
        assertEquals(employee, perform.getWorker());
    }

    @Test
    public void testAddEmployeeToList() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        employee.setFio("Дылинов Николай Андреевич");
        employees.add(employee);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testRemoveTaskFromList() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setContent("Одна из самых первых задач");
        task.setDate(new Date());
        tasks.add(task);
        tasks.remove(task);
        assertTrue(tasks.isEmpty());
    }


    @Test
    public void testRepositoryEmployee() {
        assertEquals(employeeController.getEmployeeRepository(), employeeRepository);
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setUsername("krasilnikov");
        employee.setId(5L);
        when(employeeRepository.findById(employee.getId())).thenReturn(employee);
        Employee result = employeeController.getEmployeeById(employee.getId());
        assertEquals(employee.getId(), result.getId());
    }

    @Test
    public void testRepositoryPeform() {
        assertEquals(performController.getPerformRepository(), performRepository);
    }

    @Test
    public void testGetPerformById() {
        Perform perform = new Perform();
        perform.setId(1L);
        when(performRepository.findById(perform.getId())).thenReturn(perform);
        Perform result = performController.getPerformById(perform.getId());
        assertEquals(perform.getId(), result.getId());
    }

    @Test
    public void testFacadeAddNullEmployee() {
        Employee employee = null;
        assertNull(mainFacade.addEmployee(employee));
    }

    @Test
    public void testFacadeAddNullTask() {
        Task task = null;
        assertNull(mainFacade.addTask(task));
    }

    /*@Test
    public void testGetEmployeeByUsername() {
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        when(employeeRepository.find("username", employee.getUsername()).firstResult()).thenReturn(employee);
        Employee result = employeeController.getEmployeeByUsername(employee.getUsername());
        assertEquals(employee.getUsername(), result.getUsername());
    }*/
}
