package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;

public class CarbonCreditsContext {

  private final CarbonCreditsResource carbonCreditsResource;

  public CarbonCreditsContext() {
    carbonCreditsResource = new CarbonCreditsResourceImpl();
  }

  public CarbonCreditsResource getCarbonCreditsResource() {
    return carbonCreditsResource;
  }
}
