package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/admin/carbon-credits")
public class CarbonCreditsResourceAdmin {

  private final CarbonCreditsService carbonCreditsService;

  public CarbonCreditsResourceAdmin(CarbonCreditsService carbonCreditsService) {
    this.carbonCreditsService = carbonCreditsService;
  }

  @POST
  @Path("/transfer")
  public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsService.transferRemainingBudget().asDouble();
    return response;
  }
}
