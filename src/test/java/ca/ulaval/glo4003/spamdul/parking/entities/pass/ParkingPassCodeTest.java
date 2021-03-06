package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.exceptions.CantCreatePassCodeException;
import org.junit.Test;

public class ParkingPassCodeTest {

  @Test(expected = CantCreatePassCodeException.class)
  public void givenValue_whenCreatingPassCode_shouldThrowException() {
    String value = "123";

    ParkingPassCode.valueOf(value);
  }
}
