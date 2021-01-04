package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class PermitNumberFactory {

  private final IdGenerator idGenerator;

  public PermitNumberFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public PermitNumber create() {
    return PermitNumber.valueOf(idGenerator.generate());
  }
}
