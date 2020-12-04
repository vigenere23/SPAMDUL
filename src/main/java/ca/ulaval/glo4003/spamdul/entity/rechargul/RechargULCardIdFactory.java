package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class RechargULCardIdFactory {

  private final IdGenerator<Long> idGenerator;

  public RechargULCardIdFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public RechargULCardId create() {
    return RechargULCardId.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
