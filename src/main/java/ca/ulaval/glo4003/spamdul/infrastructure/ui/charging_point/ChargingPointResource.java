package ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.dto.ChargingPointActivationRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/charging-points")
public interface ChargingPointResource {

  @GET
  Response getAll();

  @Path("/{id}")
  @GET
  Response getSingle(@PathParam("id") String chargingPointIdString);

  @Path("/{id}/activate")
  @POST
  @Consumes("application/json")
  Response activateCharging(@PathParam("id") String chargingPointIdString, ChargingPointActivationRequest request);

  @Path("/{id}/connect")
  @POST
  Response startCharging(@PathParam("id") String chargingPointIdString);

  @Path("/{id}/disconnect")
  @POST
  Response stopCharging(@PathParam("id") String chargingPointIdString);

  @Path("/{id}/deactivate")
  @POST
  Response deactivateCharging(@PathParam("id") String chargingPointIdString);
}
