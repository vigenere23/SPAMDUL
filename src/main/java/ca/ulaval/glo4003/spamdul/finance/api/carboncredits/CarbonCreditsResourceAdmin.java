package ca.ulaval.glo4003.spamdul.finance.api.carboncredits;

import ca.ulaval.glo4003.spamdul.finance.api.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.finance.usecases.carboncredits.CarbonCreditsUseCase;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/admin/carbon-credits")
public class CarbonCreditsResourceAdmin {

  private final CarbonCreditsUseCase carbonCreditsUseCase;

  public CarbonCreditsResourceAdmin(CarbonCreditsUseCase carbonCreditsUseCase) {
    this.carbonCreditsUseCase = carbonCreditsUseCase;
  }

  @POST
  @Path("/transfer")
  public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsUseCase.transferRemainingBudget().asDouble();
    return response;
  }
}
