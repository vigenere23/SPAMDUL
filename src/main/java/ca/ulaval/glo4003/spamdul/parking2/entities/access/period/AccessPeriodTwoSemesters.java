package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodTwoSemesters extends AccessPeriod {

  public AccessPeriodTwoSemesters(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "Access period for 2 semesters";
  }

  @Override public AccessPeriodType getType() {
    return AccessPeriodType.TWO_SEMESTERS;
  }
}
