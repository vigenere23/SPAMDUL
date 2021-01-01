package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class InitiativeIdFactory {

  private final IdGenerator idGenerator;

  public InitiativeIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InitiativeId create() {
    return InitiativeId.valueOf(idGenerator.generate());
  }
}
