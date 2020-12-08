package ca.ulaval.glo4003.spamdul.entity.parking.pass.car;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;

public class CarParkingPassCode extends ParkingPassCode{

  protected CarParkingPassCode(String value) {
    super(value);
  }

  public static CarParkingPassCode valueOf(String passCode) {
    return new CarParkingPassCode(passCode);
  }

}
