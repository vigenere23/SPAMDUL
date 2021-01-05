package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.LocalDateTime;

public class AccessPeriodCreationInfosSingleDay {

  private final LocalDateTime start;

  public AccessPeriodCreationInfosSingleDay(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getStart() {
    return this.start;
  }
}
