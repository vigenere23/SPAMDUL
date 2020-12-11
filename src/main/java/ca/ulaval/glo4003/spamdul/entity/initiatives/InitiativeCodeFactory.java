package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class InitiativeCodeFactory {

  private final IdGenerator idGenerator;

  public InitiativeCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InitiativeCode create() {
    return InitiativeCode.valueOf(idGenerator.generate());
  }
}
