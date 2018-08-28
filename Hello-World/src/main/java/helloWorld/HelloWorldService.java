package helloWorld;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/v0.0.0")
public class HelloWorldService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response defaultHandler() {
        return Response.status(200).entity("Hello World").build();
    }
}
