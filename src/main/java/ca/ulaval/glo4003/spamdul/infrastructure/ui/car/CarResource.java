package ca.ulaval.glo4003.spamdul.infrastructure.ui.car;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface CarResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response createCar(CarRequest carRequest);
}
