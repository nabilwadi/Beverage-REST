package de.uniba.dsg.jaxrs;

import de.uniba.dsg.jaxrs.resources.EmployeeResource;
import de.uniba.dsg.jaxrs.resources.SwaggerUI;
import de.uniba.dsg.jaxrs.resources.UserResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();
        // resources
        resources.add(EmployeeResource.class);
        resources.add(UserResource.class);

        // swagger-ui
        resources.add(SwaggerUI.class);

        return resources;
    }
}
