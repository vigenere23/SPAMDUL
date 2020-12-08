package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class PassCode extends Id {

  protected PassCode(String value) {
    super(value);
  }

  public static PassCode valueOf(String passCode) {
    return new PassCode(passCode);
  }
}
