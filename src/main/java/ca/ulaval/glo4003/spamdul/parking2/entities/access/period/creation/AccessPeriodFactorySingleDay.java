package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactorySingleDay {

  public AccessPeriod create(AccessPeriodCreationInfosSingleDay infos) {
    LocalDate date = infos.getDate();
    return new AccessPeriod(AccessPeriodType.DAY, date, date, LocalTime.MIN, LocalTime.MAX);
  }
}
