package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import java.util.concurrent.ScheduledExecutorService;

public class CarbonCreditsContext {

  private final CarbonCreditsResource carbonCreditsResource;
  private final CarbonCreditsTask carbonCreditsTask;

  public CarbonCreditsContext(ScheduledExecutorService executor) {
    CarbonCreditsService carbonCreditsService = new CarbonCreditsService();
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService);

    carbonCreditsTask = new CarbonCreditsTask(executor);
  }

  public CarbonCreditsResource getCarbonCreditsResource() {
    return carbonCreditsResource;
  }
}
