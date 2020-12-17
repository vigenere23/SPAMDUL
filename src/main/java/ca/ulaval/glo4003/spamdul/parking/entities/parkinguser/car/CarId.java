package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class CarId extends Id {

  private CarId(String value) {
    super(value);
  }

  public static CarId valueOf(String userId) {
    return new CarId(userId);
  }
}
