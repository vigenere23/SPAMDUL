package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class InfractionIdFactory {

  private final IdGenerator idGenerator;

  public InfractionIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InfractionId create() {
    return InfractionId.valueOf(idGenerator.generate());
  }
}
