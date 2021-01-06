package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

public class AccessPeriodFactory {

  private final AccessPeriodFactoryHourly accessPeriodFactoryHourly;
  private final AccessPeriodFactorySingleDay accessPeriodFactorySingleDay;
  private final AccessPeriodFactoryDayPerWeek accessPeriodFactoryDayPerWeek;
  private final AccessPeriodFactorySession accessPeriodFactorySession;
  private final AccessPeriodFactoryMonth accessPeriodFactoryMonth;

  public AccessPeriodFactory(AccessPeriodFactoryHourly accessPeriodFactoryHourly,
                             AccessPeriodFactorySingleDay accessPeriodFactorySingleDay,
                             AccessPeriodFactoryDayPerWeek accessPeriodFactoryDayPerWeek,
                             AccessPeriodFactorySession accessPeriodFactorySession,
                             AccessPeriodFactoryMonth accessPeriodFactoryMonth) {
    this.accessPeriodFactoryHourly = accessPeriodFactoryHourly;
    this.accessPeriodFactorySingleDay = accessPeriodFactorySingleDay;
    this.accessPeriodFactoryDayPerWeek = accessPeriodFactoryDayPerWeek;
    this.accessPeriodFactorySession = accessPeriodFactorySession;
    this.accessPeriodFactoryMonth = accessPeriodFactoryMonth;
  }

  public AccessPeriod create(AccessPeriodType type, AccessPeriodCreationInfos infos) {
    switch (type) {
      case HOUR:
        return accessPeriodFactoryHourly.create(infos.forHourly());
      case DAY:
        return accessPeriodFactorySingleDay.create(infos.forSingleDay());
      case DAY_PER_WEEK:
        return accessPeriodFactoryDayPerWeek.create(infos.forDayPerWeek());
      case ONE_SEMESTER:
        return accessPeriodFactorySession.create(infos.forSession(), 1);
      case TWO_SEMESTERS:
        return accessPeriodFactorySession.create(infos.forSession(), 2);
      case THREE_SEMESTERS:
        return accessPeriodFactorySession.create(infos.forSession(), 3);
      case MONTH:
        return accessPeriodFactoryMonth.create(infos.forMonth());
      default:
        throw new RuntimeException(String.format("No access period for type %s", type));
    }
  }
}
