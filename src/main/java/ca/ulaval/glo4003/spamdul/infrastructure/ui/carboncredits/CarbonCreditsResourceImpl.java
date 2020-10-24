package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;

import javax.ws.rs.core.Response;

public class CarbonCreditsResourceImpl implements CarbonCreditsResource {

  private final CarbonCreditsService carbonCreditsService;

  public CarbonCreditsResourceImpl(CarbonCreditsService carbonCreditsService) {
    this.carbonCreditsService = carbonCreditsService;
  }

  @Override
  public Response activateAutomaticTransfer(CarbonCreditsToggleDto request) {
    carbonCreditsService.activateAutomaticTransfer(request.active);

    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @Override
  public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsService.transferRemainingBudget();
    return response;
  }
}
