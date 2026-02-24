package me.andrey20005.web4.util;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        // Получаем оригинальный статус
        int status = exception.getResponse().getStatus();

        // Получаем сообщение (из response или из exception)
        String message = exception.getMessage();
        if (message == null && exception.getResponse().getEntity() != null) {
            message = exception.getResponse().getEntity().toString();
        }

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("message", message != null ? message : "Произошла ошибка");
        errorBody.put("status", status);
        errorBody.put("timestamp", System.currentTimeMillis());

        return Response.status(status)
                .entity(errorBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
