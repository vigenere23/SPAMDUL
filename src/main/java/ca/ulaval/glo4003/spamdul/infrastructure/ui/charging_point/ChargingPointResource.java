package ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/charging-points")
public interface ChargingPointResource {

  @GET
  Response getAll();

  @Path("/{id}/activate")
  @POST
  Response activateCharging(@PathParam("id") String chargingPointId);

  @Path("/{id}/start")
  @POST
  Response startCharging(@PathParam("id") String chargingPointId);

  @Path("/{id}/stop")
  @POST
  Response stopCharging(@PathParam("id") String chargingPointId);
}
