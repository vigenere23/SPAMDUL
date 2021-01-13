package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodDayPerWeek;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactoryDayPerWeek {

  private final Calendar calendar;

  public AccessPeriodFactoryDayPerWeek(Calendar calendar) {
    this.calendar = calendar;
  }

  public AccessPeriod create(AccessPeriodCreationInfosDayPerWeek infos) {
    LocalDate semesterStart = calendar.getStartOfSemester(infos.getSemester()).toLocalDate();
    LocalDate semesterEnd = calendar.getEndOfSemester(infos.getSemester()).toLocalDate();
    TimePeriod timePeriod = new TimePeriod(semesterStart,
                                           semesterEnd,
                                           LocalTime.MIN,
                                           LocalTime.MAX,
                                           infos.getDayOfWeek());

    return new AccessPeriodDayPerWeek(timePeriod);
  }
}
