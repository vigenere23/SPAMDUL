package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class InitiativeIdFactory {

  private final IdGenerator idGenerator;

  public InitiativeIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InitiativeId create() {
    return InitiativeId.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
