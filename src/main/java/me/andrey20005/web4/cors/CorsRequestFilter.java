package me.andrey20005.web4.cors;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class CorsRequestFilter implements ContainerRequestFilter {
    protected static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:5173",
            "https://localhost:14140",
            "https://localhost:14141"
    );

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        String origin = request.getHeaderString("Origin");

        if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
            if (origin != null && !ALLOWED_ORIGINS.contains(origin)) {
                request.abortWith(Response.status(403)
                        .entity("Origin not allowed: " + origin)
                        .build());
            }
        }
    }
}
