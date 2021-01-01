package ca.ulaval.glo4003.spamdul.parking.entities.pass.car;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;

public class CarParkingPassCode extends ParkingPassCode{

  protected CarParkingPassCode(String value) {
    super(value);
  }

  public static CarParkingPassCode valueOf(String passCode) {
    return new CarParkingPassCode(passCode);
  }

}
