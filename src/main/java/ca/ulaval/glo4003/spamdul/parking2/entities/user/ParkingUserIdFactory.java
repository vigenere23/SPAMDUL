package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class ParkingUserIdFactory {

  private final IdGenerator idGenerator;

  public ParkingUserIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ParkingUserId create() {
    return new ParkingUserId(idGenerator.generate());
  }
}
