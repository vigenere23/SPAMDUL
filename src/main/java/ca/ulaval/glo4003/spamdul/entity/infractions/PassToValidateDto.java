package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;

public class PassToValidateDto {

  public String passCode;
  public ParkingZone parkingZone;
  public LicensePlate licensePlate;
}
