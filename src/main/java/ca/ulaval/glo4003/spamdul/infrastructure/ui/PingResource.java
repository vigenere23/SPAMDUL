package ca.ulaval.glo4003.spamdul.infrastructure.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {

  @GET
  public Response ping() {
    return Response.ok().build();
  }
}
