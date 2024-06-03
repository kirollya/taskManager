package org.naco.api;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.naco.MainFacade;
import org.naco.models.entities.Employee;
import org.naco.models.entities.Task;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Path("/")
@RolesAllowed({"user", "admin"})
public class UserApi {

    @Inject
    MainFacade mainFacade;

    static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance myTasksInfo(List<Task> chiefTasksList, List<Task> workerTasksList, Employee currentUser, SimpleDateFormat simpleDateFormat);
        public static native TemplateInstance taskInfo(Task task, List<Employee> workers, List<Employee> allWorkers);
        public static native TemplateInstance allTasks(List<Task> tasks, List<Employee> workers, List<Employee> chiefs, SimpleDateFormat simpleDateFormat);
        public static native TemplateInstance tasksReport(List<Task> tasks, SimpleDateFormat simpleDateFormat);
    }

    @GET
    @Path("/myTasks")
    public TemplateInstance getMyTasks(@Context SecurityContext securityContext) {
        Employee employee = mainFacade.getEmployeeByLogin(securityContext.getUserPrincipal().getName());
        return Templates.myTasksInfo(mainFacade.getTasksAsChief(employee), mainFacade.getTasksAsWorker(employee), employee, UserApi.simpleDateFormat);
    }

    @GET
    @Path("/task/getInfo/{id}")
    public TemplateInstance getTaskInfo(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        Task task = mainFacade.getTaskById(id);
        Employee me = mainFacade.getEmployeeByLogin(securityContext.getUserPrincipal().getName());
        return Templates.taskInfo(task, mainFacade.getWorkersForTask(task), mainFacade.getEmployeesForChief(me, task));
    }

    @POST
    @Path("/task/updTaskCompleted/{id}")
    public Response updTaskCompleted(@PathParam("id") Long id, Boolean completed) {
        mainFacade.updTaskCompleted(id, completed);
        return Response.ok().build();
    }

    @GET
    @Path("/task/getInfoWithFilter")
    public TemplateInstance getInfoWithFilter(@QueryParam("workerId") Long workerId, @QueryParam("chiefId") Long chiefId, @QueryParam("date") String date) {
        GregorianCalendar gregorianCalendar = null;
        if (date != null && !date.isBlank()) {
            String[] dateParts = date.split("-");
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]), 23, 59, 59);
        }
        return Templates.allTasks(mainFacade.getTasksByParams(workerId, chiefId, gregorianCalendar != null ? gregorianCalendar.getTime() : null), mainFacade.getAllEmployees(), mainFacade.getAllChiefs(), UserApi.simpleDateFormat);
    }

    @GET
    @Path("/task/makeReport")
    public TemplateInstance getTasksReport(@QueryParam("workerId") Long workerId, @QueryParam("chiefId") Long chiefId, @QueryParam("date") String date) {
        GregorianCalendar gregorianCalendar = null;
        if (date != null && !date.isBlank()) {
            String[] dateParts = date.split("-");
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]), 23, 59, 59);
        }
        return Templates.tasksReport(mainFacade.getTasksByParams(workerId, chiefId, gregorianCalendar != null ? gregorianCalendar.getTime() : null), UserApi.simpleDateFormat);
    }

}
