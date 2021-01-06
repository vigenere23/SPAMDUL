package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AccessPeriodCreationInfosHourly {

  private final LocalDateTime start;
  private final int numberOfHours;

  public AccessPeriodCreationInfosHourly(LocalDate date, LocalTime startTime, int numberOfHours) {
    this.start = LocalDateTime.of(date, startTime);
    this.numberOfHours = numberOfHours;
  }

  public LocalDateTime getStart() {
    return this.start;
  }

  public int getNumberOfHours() {
    return this.numberOfHours;
  }
}
