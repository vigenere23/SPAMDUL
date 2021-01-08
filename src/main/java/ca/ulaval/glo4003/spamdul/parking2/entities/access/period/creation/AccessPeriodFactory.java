package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;

public class AccessPeriodFactory {

  private final AccessPeriodFactoryHourly accessPeriodFactoryHourly;
  private final AccessPeriodFactorySingleDay accessPeriodFactorySingleDay;
  private final AccessPeriodFactoryDayPerWeek accessPeriodFactoryDayPerWeek;
  private final AccessPeriodFactorySemester accessPeriodFactorySemester;
  private final AccessPeriodFactoryMonth accessPeriodFactoryMonth;

  public AccessPeriodFactory(AccessPeriodFactoryHourly accessPeriodFactoryHourly,
                             AccessPeriodFactorySingleDay accessPeriodFactorySingleDay,
                             AccessPeriodFactoryDayPerWeek accessPeriodFactoryDayPerWeek,
                             AccessPeriodFactorySemester accessPeriodFactorySemester,
                             AccessPeriodFactoryMonth accessPeriodFactoryMonth) {
    this.accessPeriodFactoryHourly = accessPeriodFactoryHourly;
    this.accessPeriodFactorySingleDay = accessPeriodFactorySingleDay;
    this.accessPeriodFactoryDayPerWeek = accessPeriodFactoryDayPerWeek;
    this.accessPeriodFactorySemester = accessPeriodFactorySemester;
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
        return accessPeriodFactorySemester.create(infos.forSession(), 1);
      case TWO_SEMESTERS:
        return accessPeriodFactorySemester.create(infos.forSession(), 2);
      case THREE_SEMESTERS:
        return accessPeriodFactorySemester.create(infos.forSession(), 3);
      case MONTH:
        return accessPeriodFactoryMonth.create(infos.forMonth());
      default:
        throw new RuntimeException(String.format("No access period for type %s", type));
    }
  }
}
