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
    }

    @GET
    @Path("/myTasks")
    public TemplateInstance getMyTasks(@Context SecurityContext securityContext) {
        Employee employee = mainFacade.getEmployeeByLogin(securityContext.getUserPrincipal().getName());
        return Templates.myTasksInfo(mainFacade.getTasksAsChief(employee), mainFacade.getTasksAsWorker(employee), employee, UserApi.simpleDateFormat);
    }

    @POST
    @Path("/task/updTaskCompleted/{id}")
    public Response updTaskCompleted(@PathParam("id") Long id, Boolean completed) {
        mainFacade.updTaskCompleted(id, completed);
        return Response.ok().build();
    }

}
