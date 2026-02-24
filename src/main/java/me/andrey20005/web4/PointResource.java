package me.andrey20005.web4;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import me.andrey20005.web4.Entity.Point;
import me.andrey20005.web4.repository.UserRepository;
import me.andrey20005.web4.service.PointService;

import java.util.List;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {

    @Inject
    private PointService pointService;

    @Inject
    private UserRepository userRepository;

    @GET
    public List<Point> getAll(@Context SecurityContext securityContext) {
        Long userId = Long.valueOf(securityContext.getUserPrincipal().getName());
        System.out.println("getAll user = " + userId);
        return pointService.findByUserId(userId);
    }

    @GET
    @Path("/{id}")
    public Point getById(@PathParam("id") Long id) {
        return pointService.findById(id);
    }

    @POST
    public Response create(Point point, @Context SecurityContext securityContext) {
        Long userId = Long.valueOf(securityContext.getUserPrincipal().getName());
        System.out.println("create user = " + userId);
        point.setUserId(userId);
        return Response.status(201).entity(pointService.create(point)).build();
    }

    @PUT
    @Path("/{id}")
    public Point update(@PathParam("id") Long id, Point point) {
        return pointService.update(id, point);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        pointService.delete(id);
    }
}