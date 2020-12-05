package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassCodeFormat;

public class PassCode extends LongId {

  private PassCode(long value) {
    super(value);
  }

  public static PassCode valueOf(String passCode) {
    try {
      return new PassCode(Long.parseLong(passCode));

    } catch (NumberFormatException e) {
      throw new InvalidPassCodeFormat("Invalid pass code format");
    }
  }
}
