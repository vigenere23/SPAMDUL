package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
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
      throw new IllegalArgumentException("number of sessions must be between 1 and 3");
    }

    LocalDate periodStart = calendar.getStartOfSemester(infos.getSemester()).toLocalDate();
    LocalDate periodEnd = calendar.getEndOfSemester(infos.getSemester().plusSemesters(numberOfSemesters))
                                  .toLocalDate();

    // TODO create polymorphic accessPeriod with timePeriods
    // it could then calculate its own price

    AccessPeriodType periodType = null;

    switch (numberOfSemesters) {
      case 1:
        periodType = AccessPeriodType.ONE_SEMESTER;
        break;
      case 2:
        periodType = AccessPeriodType.TWO_SEMESTERS;
        break;
      case 3:
        periodType = AccessPeriodType.THREE_SEMESTERS;
        break;
    }

    return new AccessPeriod(periodType, periodStart, periodEnd, LocalTime.MIN, LocalTime.MAX);
  }
}
