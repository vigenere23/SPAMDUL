package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/infractions")
public interface InfractionResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response validateParkingPass(InfractionRequest infractionRequest);

  @POST
  @Path("/pay")
  @Consumes(MediaType.APPLICATION_JSON)
  Response payInfraction(InfractionPaymentRequest infractionPaymentRequest);
}
