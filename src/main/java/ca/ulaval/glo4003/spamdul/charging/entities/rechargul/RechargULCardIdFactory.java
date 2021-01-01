package ca.ulaval.glo4003.spamdul.charging.entities.rechargul;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class RechargULCardIdFactory {

  private final IdGenerator idGenerator;

  public RechargULCardIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public RechargULCardId create() {
    return RechargULCardId.valueOf(idGenerator.generate());
  }
}
