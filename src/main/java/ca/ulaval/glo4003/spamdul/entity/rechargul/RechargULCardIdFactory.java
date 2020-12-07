package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class RechargULCardIdFactory {

  private final IdGenerator idGenerator;

  public RechargULCardIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public RechargULCardId create() {
    return RechargULCardId.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
