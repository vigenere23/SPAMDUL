package ca.ulaval.glo4003.spamdul.entity.parking.pass.bike;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;

public class BikeParkingPassCode extends ParkingPassCode {

  protected BikeParkingPassCode(String value) {
    super(value);
  }

  public static BikeParkingPassCode valueOf(String value) {
    return new BikeParkingPassCode(value);
  }
}
