package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class PassCodeFactory {

  private final IdGenerator<Long> idGenerator;

  public PassCodeFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public PassCode create() {
    return PassCode.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
