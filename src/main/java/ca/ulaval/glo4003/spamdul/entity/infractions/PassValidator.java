package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalTime;

public class PassValidator {

  public InfractionCode validate(Pass pass, ParkingZone parkingZone, LocalTime localTime) {
    boolean isParkingZoneValid = pass.isAValidParkingZone(parkingZone);
    if (!isParkingZoneValid) {
      return InfractionCode.valueOf("ZONE_01");
    } else {
      return null;
    }
  }
}
