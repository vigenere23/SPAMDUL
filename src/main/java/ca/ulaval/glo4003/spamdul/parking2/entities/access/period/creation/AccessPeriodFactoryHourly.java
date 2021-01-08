package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodHour;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;
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
    TimePeriod timePeriod = new TimePeriod(periodStart, periodEnd, startTime, endTime);

    return new AccessPeriodHour(timePeriod);
  }
}
