package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimePeriodValidator extends PassValidator {

  public void validate(Pass pass, ParkingZone parkingZone, LocalTime time) {
    if (!pass.getTimePeriod().include(time.atDate(LocalDate.now()))) {
      throw new InfractionException("VIG_01");
    } else {
      super.validate(pass, parkingZone, time);
    }
  }


}
