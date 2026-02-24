package me.andrey20005.web4;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.Priority;

import me.andrey20005.web4.util.JwtUtil;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Provider
@Priority(jakarta.ws.rs.Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login"
            , "/auth/register"
            , "/time"
//            , "/points"
            , "/auth/login"
            , "/auth/register"
    );

    public static final String SECRET_KEY = "secret-key-jhgbhgugbijhbjuhguhgujhuhgbuhujhjhhgbuyguhgbuyguyhgbuyhgb6546561616516516";
    static {
        System.out.println(SECRET_KEY);
    }

    @Override
    public void filter(ContainerRequestContext request) {
        String path = request.getUriInfo().getPath();

        if (isPublicPath(path)) {
            return;
        }

        String authHeader = request.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            request.abortWith(Response
                    .status(401)
                    .entity(Map.of("error", "Authorization header missing"))
                    .build());
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = JwtUtil.validateToken(token, SECRET_KEY);
            Long userId = claims.get("userId", Long.class);
            String username = claims.get("username", String.class);

            request.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> String.valueOf(userId);
                }

                @Override
                public boolean isUserInRole(String role) {
                    return false;
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });
        } catch (JwtException e) {
            request.abortWith(Response
                    .status(401)
                    .entity(Map.of("error", "Invalid or expired token"))
                    .build());
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
