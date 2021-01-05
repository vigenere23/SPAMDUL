package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public abstract class AccessPeriodFactory {

  protected final Calendar calendar;

  protected AccessPeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  public abstract AccessPeriod create(LocalDateTime start, int multiplier, DayOfWeek dayOfWeek, Semester semester);
}
