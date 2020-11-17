package ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/charging-point/:id")
public interface ChargingPointResource {

  @Path("/activate")
  @POST
  Response activateCharging();

  @Path("/start")
  @POST
  Response startCharging();

  @Path("/stop")
  @POST
  Response stopCharging();
}
