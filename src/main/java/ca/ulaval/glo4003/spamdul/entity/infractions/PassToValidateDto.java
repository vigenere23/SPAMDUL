package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;

public class PassToValidateDto {

  public String passCode;
  public ParkingZone parkingZone;
  public LicensePlate licensePlate;
}
