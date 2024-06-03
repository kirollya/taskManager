package org.naco.api;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.naco.MainFacade;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Post;
import org.naco.models.entities.Task;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Path("/admin")
@RolesAllowed("admin")
public class AdminApi {

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    @Inject
    MainFacade mainFacade;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance fullDbInfo(List<Employee> employees, List<Task> tasks, List<Perform> performs, List<Post> posts, DateTimeFormatter dateTimeFormatter);
        public static native TemplateInstance taskManagment();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance fullDbInfo() {
        List<Employee> employees = mainFacade.getAllEmployees();
        List<Task> tasks = mainFacade.getAllTasks();
        List<Perform> performs = mainFacade.getAllPerforms();
        return Templates.fullDbInfo(employees, tasks, performs, Post.getAll(), CUSTOM_FORMATTER);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/employee/add")
    public Response addEmployee(Employee employee) {
        if (mainFacade.addEmployee(employee) == null)
            return Response.serverError().build();
        try {
            return Response.ok().location(new URI("http://localhost:8080/")).build();
        } catch(URISyntaxException exception) {
            System.out.println(exception.getClass().getName() + ": " + exception.getMessage());
            return Response.ok().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/employee/delete/{id}")
    public Response deleteEmployee(@PathParam("id") Long id) {
        try {
            if (!mainFacade.deleteEmployee(id))
                return Response.serverError().build();
            return Response.ok().location(new URI("http://localhost:8080/")).build();
        } catch(URISyntaxException exception) {
            System.out.println(exception.getClass().getName() + ": " + exception.getMessage());
            return Response.ok().build();
        } catch (Exception exception) {
            System.out.println("Unable to delete employee with id = " + id + ", because of links on it");
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employee/get/{id}")
    public Response getEmployee(@PathParam("id") Long id) {
        return Response.ok(mainFacade.getEmployee(id)).build();
    }

    @POST
    @Path("/employee/updEmployeeRank/{id}")
    public Response updEmployeeId(@PathParam("id") Long id, Integer rank) {
        mainFacade.updEmployeeRank(id, rank);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/task/add")
    public Response addTask(Task task) {
        if (mainFacade.addTask(task) == null)
            return Response.serverError().build();
        try {
            return Response.ok().location(new URI("http://localhost:8080/")).build();
        } catch(URISyntaxException exception) {
            System.out.println(exception.getClass().getName() + ": " + exception.getMessage());
            return Response.ok().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/task/delete/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        try {
            if (!mainFacade.deleteTask(id))
                return Response.serverError().build();
            return Response.ok().location(new URI("http://localhost:8080/myTasks")).build();
        } catch(URISyntaxException exception) {
            System.out.println(exception.getClass().getName() + ": " + exception.getMessage());
            return Response.ok().build();
        } catch (Exception exception) {
            System.out.println("Unable to delete task with id = " + id + ", because of links on it");
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/task/updTaskDate/{id}")
    public Response updTaskDate(@PathParam("id") Long id, Date date) {
        mainFacade.updTaskDate(id, date);
        return Response.ok().build();
    }

    @POST
    @Path("/task/{task_id}/assignWorker_{worker_id}")
    public Response assignWorker(@PathParam("worker_id") Long worker_id, @PathParam("task_id") Long task_id) {
        mainFacade.assignWorker(task_id, worker_id);
        return Response.ok().build();
    }

}
