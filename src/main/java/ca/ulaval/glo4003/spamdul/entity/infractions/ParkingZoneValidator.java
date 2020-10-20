package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalTime;

public class ParkingZoneValidator extends PassValidator {

  public void validate(Pass pass, ParkingZone parkingZone, LocalTime time) {
    if (!pass.isAValidParkingZone(parkingZone)) {
      throw new InfractionException("ZONE_01");
    } else {
      super.validate(pass, parkingZone, time);
    }
  }
}
