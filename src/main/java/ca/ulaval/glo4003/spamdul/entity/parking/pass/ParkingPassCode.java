package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.CantCreatePassCodeException;

public abstract class ParkingPassCode extends Id {

  protected ParkingPassCode(String value) {
    super(value);
  }

  public static ParkingPassCode valueOf(String passCode) {
    throw new CantCreatePassCodeException();
  }
}
