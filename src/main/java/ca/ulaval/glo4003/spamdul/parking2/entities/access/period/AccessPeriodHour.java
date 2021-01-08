package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodHour extends AccessPeriod {

  public AccessPeriodHour(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "Access period for 1 hour";
  }

  @Override public AccessPeriodType getType() {
    return AccessPeriodType.HOUR;
  }
}
