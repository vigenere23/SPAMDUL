package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class InfractionIdFactory {

  private final IdGenerator<Long> idGenerator;

  public InfractionIdFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InfractionId create() {
    return InfractionId.valueOf(String.valueOf(idGenerator.getNextId()));
  }
}
