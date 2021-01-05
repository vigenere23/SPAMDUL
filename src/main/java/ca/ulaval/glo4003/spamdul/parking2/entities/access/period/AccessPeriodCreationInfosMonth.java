package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.Month;

public class AccessPeriodCreationInfosMonth {

  private final int year;
  private final Month month;

  public AccessPeriodCreationInfosMonth(int year, Month month) {
    this.year = year;
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public Month getMonth() {
    return month;
  }
}
