package me.andrey20005.web4.cors;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class CorsResponseFilter implements ContainerResponseFilter {
    private static final List<String> ALLOWED_ORIGINS = CorsRequestFilter.ALLOWED_ORIGINS;

    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) {

        String origin = request.getHeaderString("Origin");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
        }

        // Добавляем заголовки только для разрешённых доменов
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "Content-Type, Authorization");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS");
        }
    }
}
