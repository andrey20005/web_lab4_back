package me.andrey20005.web4;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.andrey20005.web4.Entity.Point;
import me.andrey20005.web4.service.PointService;

import java.util.List;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {

    @Inject
    private PointService pointService;

    @GET
    public List<Point> getAll() {
        return pointService.findAll();
    }

    @GET
    @Path("/{id}")
    public Point getById(@PathParam("id") Long id) {
        return pointService.findById(id);
    }

    @POST
    public Response create(Point point) {
        Point created = pointService.create(point);
        return Response.status(201).entity(created).build();
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