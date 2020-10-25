package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/admin/carbon-credits")
public interface CarbonCreditsResourceAdmin {

  @POST
  @Path("/transfer")
  CarbonCreditsTransferResponse transferFundsToCarbonCredits();
}
