package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import java.time.LocalTime;

public class PassToValidateDto {

  public String passCode;
  public ParkingZone parkingZone;
}
