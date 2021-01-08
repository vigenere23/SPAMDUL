package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodOneSemester extends AccessPeriod {

  public AccessPeriodOneSemester(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "Access period for 1 semester";
  }

  @Override public AccessPeriodType getType() {
    return AccessPeriodType.ONE_SEMESTER;
  }
}
