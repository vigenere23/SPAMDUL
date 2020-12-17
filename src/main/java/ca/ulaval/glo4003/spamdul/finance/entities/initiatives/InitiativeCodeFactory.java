package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class InitiativeCodeFactory {

  private final IdGenerator idGenerator;

  public InitiativeCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InitiativeCode create() {
    return InitiativeCode.valueOf(idGenerator.generate());
  }
}
