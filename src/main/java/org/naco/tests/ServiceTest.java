package org.naco.tests;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.naco.controllers.EmployeeController;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Post;
import org.naco.models.entities.Task;
import org.naco.models.repositories.EmployeeRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
public class ServiceTest {

    EmployeeRepository employeeRepository;
    EmployeeController employeeController;

    @Before
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeController = new EmployeeController(employeeRepository);
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
    public void testRepositoryEmployee(){
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
    public void testGetEmployeeByUsername() {
        Employee employee = new Employee();
        employee.setUsername("dylinov");
        when(employeeRepository.find("username", employee.getUsername()).firstResult()).thenReturn(employee);
        Employee result = employeeController.getEmployeeByUsername(employee.getUsername());
        assertEquals(employee.getUsername(), result.getUsername());
    }
}
