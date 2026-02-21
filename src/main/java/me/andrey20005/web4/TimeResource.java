package me.andrey20005.web4;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {
    @GET
    public Map<String, String> getCurrentTime() {
        return Map.of(
                "time", LocalDateTime.now().toString(),
                "timezone", ZoneId.systemDefault().toString()
        );
    }
}
