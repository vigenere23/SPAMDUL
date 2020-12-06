package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class PassCodeFactory {

  private final IdGenerator idGenerator;

  public PassCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public PassCode create() {
    return PassCode.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
