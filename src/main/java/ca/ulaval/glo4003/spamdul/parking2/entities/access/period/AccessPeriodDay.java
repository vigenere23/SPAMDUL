package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodDay extends AccessPeriod {

  public AccessPeriodDay(TimePeriod timePeriod) {
    super(timePeriod);
  }

  @Override public String toString() {
    return "access period for 1 day";
  }

  @Override protected AccessPeriodType getType() {
    return AccessPeriodType.DAY;
  }
}
