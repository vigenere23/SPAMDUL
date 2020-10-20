package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalDate;
import java.time.LocalTime;

public class PassValidator {

  public InfractionCode validate(Pass pass, ParkingZone parkingZone, LocalTime localTime) {

    boolean isParkingZoneValid = pass.isAValidParkingZone(parkingZone);
    if (!isParkingZoneValid) {
      return InfractionCode.valueOf("ZONE_01");
    } else if (!pass.getTimePeriod().include(localTime.atDate(LocalDate.now()))) {
      return InfractionCode.valueOf("VIG_01");
    }
    return null;
  }

  public InfractionCode validateInvalidPass(){
    return InfractionCode.valueOf("VIG_02");
  }

  public InfractionCode validateNoPass() {
    return InfractionCode.valueOf("VIG_03");
  }
}
