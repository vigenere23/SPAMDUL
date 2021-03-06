package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodMonth extends AccessPeriod {

  public AccessPeriodMonth(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "access period for 1 month";
  }

  @Override protected AccessPeriodType getType() {
    return AccessPeriodType.MONTH;
  }
}
