package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import java.time.LocalTime;

public class InfractionValidationDto {

  public PassCode passCode;
  public LocalTime time;
  public ParkingZone parkingZone;
}
