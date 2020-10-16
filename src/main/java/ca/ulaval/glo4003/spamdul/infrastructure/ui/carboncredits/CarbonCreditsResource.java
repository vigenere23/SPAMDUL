package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/carbon-credits")
public interface CarbonCreditsResource {

  @PUT
  @Path("/toggle")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  CarbonCreditsToggleDto toggleAutomaticTransfer(CarbonCreditsToggleDto request);

  @POST
  @Path("/transfer")
  CarbonCreditsTransferResponse transferFundsToCarbonCredits();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  CarbonCreditsTransferResponse getAllTransferredCredits();
}
