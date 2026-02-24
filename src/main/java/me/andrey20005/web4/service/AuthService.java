package me.andrey20005.web4.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import me.andrey20005.web4.Entity.User;
import me.andrey20005.web4.repository.UserRepository;
import me.andrey20005.web4.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Map;

import static me.andrey20005.web4.JwtAuthenticationFilter.SECRET_KEY;

@Stateless
public class AuthService {

    @Inject
    private UserRepository userRepository;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(
                        Response.status(401).entity(Map.of("error", "Неверный логин или пароль")).build()
                ));

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new WebApplicationException(
                    Response.status(401).entity(Map.of("error", "Неверный логин или пароль")).build()
            );
        }

        String token = JwtUtil.generateToken(user.getId(), user.getUsername(), SECRET_KEY);

        return Map.of(
                "token", token,
                "user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername()
                )
        );
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public User register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new WebApplicationException(
                    Response.status(400).entity(Map.of("error", "Имя пользователя не может быть пустым")).build()
            );
        }

        if (password == null || password.length() < 6) {
            throw new WebApplicationException(
                    Response.status(400).entity(Map.of("error", "Пароль должен быть не менее 6 символов")).build()
            );
        }

        if (userRepository.existsByUsername(username)) {
            throw new WebApplicationException(
                    Response.status(409).entity(Map.of("error", "Пользователь с таким именем уже существует")).build()
            );
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, passwordHash);

        return userRepository.create(user);
    }
}
