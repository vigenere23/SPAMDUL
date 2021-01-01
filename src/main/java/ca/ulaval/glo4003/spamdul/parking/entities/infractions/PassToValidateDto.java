package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;

public class PassToValidateDto {

  public String passCode;
  public ParkingZone parkingZone;
  public LicensePlate licensePlate;
}
