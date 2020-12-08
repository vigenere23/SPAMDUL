package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class InfractionIdFactory {

  private final IdGenerator idGenerator;

  public InfractionIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InfractionId create() {
    return InfractionId.valueOf(String.valueOf(idGenerator.generate()));
  }
}
