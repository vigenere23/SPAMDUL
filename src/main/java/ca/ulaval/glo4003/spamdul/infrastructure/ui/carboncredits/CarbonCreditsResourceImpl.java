package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;

public class CarbonCreditsResourceImpl implements CarbonCreditsResource {

  private final CarbonCreditsService carbonCreditsService;

  public CarbonCreditsResourceImpl(CarbonCreditsService carbonCreditsService) {
    this.carbonCreditsService = carbonCreditsService;
  }

  @Override
  public CarbonCreditsToggleDto toggleAutomaticTransfer(CarbonCreditsToggleDto request) {
    CarbonCreditsToggleDto response = new CarbonCreditsToggleDto();
    response.active = carbonCreditsService.toggle(request.active);

    return response;
  }

  @Override
  public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsService.transferRemainingBudget();
    return response;
  }

  @Override
  public CarbonCreditsTransferResponse getAllTransferredCredits() {
    CarbonCreditsTransferResponse response = new CarbonCreditsTransferResponse();
    response.transferred = carbonCreditsService.getTotalCarbonCredits();
    return response;
  }
}
