package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.exceptions.CantCreatePassCodeException;
import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public abstract class ParkingPassCode extends Id {

  protected ParkingPassCode(String value) {
    super(value);
  }

  public static ParkingPassCode valueOf(String passCode) {
    throw new CantCreatePassCodeException();
  }
}
