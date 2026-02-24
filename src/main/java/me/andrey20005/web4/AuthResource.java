package me.andrey20005.web4;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.andrey20005.web4.service.AuthService;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    public Response login(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return Response.status(400)
                    .entity(Map.of("error", "Требуется username и password"))
                    .build();
        }

        Map<String, Object> result = authService.login(username, password);
        return Response.ok(result).build();
    }

    @POST
    @Path("/register")
    public Response register(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            authService.register(username, password);
            return Response.status(201)
                    .entity(Map.of("message", "Пользователь успешно зарегистрирован"))
                    .build();
        } catch (WebApplicationException e) {
            throw e;
        }
    }
}
