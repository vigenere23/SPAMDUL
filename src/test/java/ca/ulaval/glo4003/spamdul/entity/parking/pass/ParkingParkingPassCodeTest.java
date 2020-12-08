package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.CantCreatePassCodeException;
import org.junit.Test;

public class ParkingParkingPassCodeTest {

  @Test(expected = CantCreatePassCodeException.class)
  public void givenValue_whenCreatingPassCode_shouldThrowException() {
    String value = "123";

    ParkingPassCode.valueOf(value);
  }
}
