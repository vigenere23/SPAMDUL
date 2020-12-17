package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class UserIdFactory {

  private final IdGenerator idGenerator;

  public UserIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public UserId create() {
    return UserId.valueOf(idGenerator.generate());
  }
}
