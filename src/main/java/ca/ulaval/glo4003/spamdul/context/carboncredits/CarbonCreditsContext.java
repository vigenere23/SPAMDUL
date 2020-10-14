package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;

public class CarbonCreditsContext {

  private final CarbonCreditsResource carbonCreditsResource;

  public CarbonCreditsContext() {
    CarbonCreditsService carbonCreditsService = new CarbonCreditsService();
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService);
  }

  public CarbonCreditsResource getCarbonCreditsResource() {
    return carbonCreditsResource;
  }
}
