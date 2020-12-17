package ca.ulaval.glo4003.spamdul.parking.entities.pass.bike;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;

public class BikeParkingPassCode extends ParkingPassCode {

  protected BikeParkingPassCode(String value) {
    super(value);
  }

  public static BikeParkingPassCode valueOf(String value) {
    return new BikeParkingPassCode(value);
  }
}
