package ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;

public class BikeParkingAccessCode extends PassCode {

  protected BikeParkingAccessCode(String value) {
    super(value);
  }

  public static BikeParkingAccessCode valueOf(String value) {
    return new BikeParkingAccessCode(value);
  }
}
