package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class ParkingUserId extends Id {

  private ParkingUserId(String value) {
    super(value);
  }

  public static ParkingUserId valueOf(String value) {
    return new ParkingUserId(value);
  }
}
