package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactoryMonth {

  public AccessPeriod create(AccessPeriodCreationInfosMonth infos) {
    LocalDate periodStart = LocalDate.of(infos.getYear(), infos.getMonth().getValue(), 1);
    LocalDate periodEnd = periodStart.withDayOfMonth(periodStart.lengthOfMonth());

    return new AccessPeriod(periodStart, periodEnd, LocalTime.MIN, LocalTime.MAX);
  }
}
