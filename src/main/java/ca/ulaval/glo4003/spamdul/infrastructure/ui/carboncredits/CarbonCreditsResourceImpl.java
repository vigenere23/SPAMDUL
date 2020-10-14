package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;

public class CarbonCreditsResourceImpl implements CarbonCreditsResource {

  @Override public CarbonCreditsToggleResponse toggleAutomaticTransfer() {
    return new CarbonCreditsToggleResponse();
  }

  @Override public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
    return new CarbonCreditsTransferResponse();
  }
}
