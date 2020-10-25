package ca.ulaval.glo4003.spamdul.context;

import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.pass.InMemoryPassRepository;

public class GlobalContext {

  public PassRepository passRepository;

  public GlobalContext() {
    passRepository = new InMemoryPassRepository();
  }
}
