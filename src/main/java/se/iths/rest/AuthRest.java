package se.iths.rest;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class AuthRest {

    @Path("admin")
    @GET
    @RolesAllowed({"ADMIN"})
    public Response admin() {
        return Response.ok("Welcome admin!").build();
    }

    @Path("user")
    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public Response user() {
        return Response.ok("Welcome user!").build();
    }

    @Path("no-access")
    @GET
    @DenyAll
    public Response noAccess() {
        return Response.ok("No access").build();
    }

    @Path("all-access")
    @GET
    @PermitAll
    public Response allAccess() {
        return Response.ok("Welcome everyone!").build();
    }





}
