package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.LocalDate;

public class AccessPeriodCreationInfosSingleDay {

  private final LocalDate date;

  public AccessPeriodCreationInfosSingleDay(LocalDate date) {
    this.date = date;
  }

  public LocalDate getDate() {
    return this.date;
  }
}
