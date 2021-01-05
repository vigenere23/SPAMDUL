package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;

public abstract class AccessPeriodFactory {

  protected final Calendar calendar;

  protected AccessPeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  public AccessPeriod create(AccessPeriodType type, AccessPeriodCreationInfos infos) {
    switch (type) {
      case HOUR:
        return new AccessPeriodFactoryHourly().create(infos.forHourly());
      case DAY:
        return new AccessPeriodFactorySingleDay().create(infos.forSingleDay());
      case DAY_PER_WEEK:
        return new AccessPeriodFactoryDayPerWeek(calendar).create(infos.forDayPerWeek());
      case ONE_SEMESTER:
        return new AccessPeriodFactorySession(calendar).create(infos.forSession(), 1);
      case TWO_SEMESTERS:
        return new AccessPeriodFactorySession(calendar).create(infos.forSession(), 2);
      case THREE_SEMESTERS:
        return new AccessPeriodFactorySession(calendar).create(infos.forSession(), 3);
      case MONTH:
        return new AccessPeriodFactoryMonth().create(infos.forMonth());
      default:
        throw new RuntimeException(String.format("No access period for type %s", type));
    }
  }
}
