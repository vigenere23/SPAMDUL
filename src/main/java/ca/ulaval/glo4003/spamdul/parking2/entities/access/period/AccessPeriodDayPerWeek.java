package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodDayPerWeek extends AccessPeriod {

  public AccessPeriodDayPerWeek(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "access period for 1 day per week, for 1 semester";
  }

  @Override protected AccessPeriodType getType() {
    return AccessPeriodType.DAY_PER_WEEK;
  }
}
