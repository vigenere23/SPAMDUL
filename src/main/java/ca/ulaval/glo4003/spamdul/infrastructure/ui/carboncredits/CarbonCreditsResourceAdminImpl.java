package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;

public class CarbonCreditsResourceAdminImpl implements CarbonCreditsResourceAdmin {

  private final CarbonCreditsService carbonCreditsService;

  public CarbonCreditsResourceAdminImpl(CarbonCreditsService carbonCreditsService) {
    this.carbonCreditsService = carbonCreditsService;
  }

  @Override
  public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsService.transferRemainingBudget();
    return response;
  }
}
