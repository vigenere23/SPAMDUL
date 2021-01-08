package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodOneSemester;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodThreeSemesters;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodTwoSemesters;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactorySemester {

  private final Calendar calendar;

  public AccessPeriodFactorySemester(Calendar calendar) {
    this.calendar = calendar;
  }

  public AccessPeriod create(AccessPeriodCreationInfosSemesters infos, int numberOfSemesters) {
    if (numberOfSemesters > 3 || numberOfSemesters < 1) {
      throw new IllegalArgumentException("number of sessions must be between 1 and 3");
    }

    LocalDate periodStart = calendar.getStartOfSemester(infos.getSemester()).toLocalDate();
    LocalDate periodEnd = calendar.getEndOfSemester(infos.getSemester().plusSemesters(numberOfSemesters))
                                  .toLocalDate();
    TimePeriod timePeriod = new TimePeriod(periodStart, periodEnd, LocalTime.MIN, LocalTime.MAX);

    switch (numberOfSemesters) {
      case 1:
        return new AccessPeriodOneSemester(timePeriod);
      case 2:
        return new AccessPeriodTwoSemesters(timePeriod);
      case 3:
        return new AccessPeriodThreeSemesters(timePeriod);
      default:
        throw new IllegalArgumentException("number of sessions must be between 1 and 3");
    }
  }
}
