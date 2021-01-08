package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodThreeSemesters extends AccessPeriod {

  public AccessPeriodThreeSemesters(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "access period for 3 semesters";
  }

  @Override protected AccessPeriodType getType() {
    return AccessPeriodType.THREE_SEMESTERS;
  }
}
