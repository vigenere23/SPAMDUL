package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactorySession {

  private final Calendar calendar;

  public AccessPeriodFactorySession(Calendar calendar) {
    this.calendar = calendar;
  }

  public AccessPeriod create(AccessPeriodCreationInfosSession infos, int numberOfSemesters) {
    if (numberOfSemesters > 3 || numberOfSemesters < 1) {
      // TODO be more specific
      throw new RuntimeException("number of sessions must be between 1 and 3");
    }

    LocalDate periodStart = calendar.getStartOfSemester(infos.getSemester()).toLocalDate();
    LocalDate periodEnd = calendar.getEndOfSemester(infos.getSemester().plusSemesters(numberOfSemesters))
                                  .toLocalDate();

    return new AccessPeriod(periodStart, periodEnd, LocalTime.MIN, LocalTime.MAX);
  }
}
