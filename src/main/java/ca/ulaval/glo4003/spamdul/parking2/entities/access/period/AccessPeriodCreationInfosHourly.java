package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.LocalDateTime;

public class AccessPeriodCreationInfosHourly {

  private final LocalDateTime start;
  private final int numberOfHours;

  public AccessPeriodCreationInfosHourly(LocalDateTime start, int numberOfHours) {
    this.start = start;
    this.numberOfHours = numberOfHours;
  }

  public LocalDateTime getStart() {
    return this.start;
  }

  public int getNumberOfHours() {
    return this.numberOfHours;
  }
}
