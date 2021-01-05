package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AccessPeriodFactoryHourly {

  public AccessPeriod create(AccessPeriodCreationInfosHourly infos) {
    LocalDateTime end = infos.getStart().plusHours(infos.getNumberOfHours());
    LocalDate periodStart = infos.getStart().toLocalDate();
    LocalDate periodEnd = end.toLocalDate();
    LocalTime startTime = infos.getStart().toLocalTime();
    LocalTime endTime = end.toLocalTime();

    return new AccessPeriod(periodStart, periodEnd, startTime, endTime);
  }
}
