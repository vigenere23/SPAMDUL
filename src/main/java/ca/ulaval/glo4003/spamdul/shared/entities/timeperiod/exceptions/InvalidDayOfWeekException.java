package ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions;

import java.time.LocalDateTime;

public class InvalidDayOfWeekException extends TimePeriodException {

  public InvalidDayOfWeekException(LocalDateTime localDateTime) {
    super(String.format("%s is out of period days of week boundaries", localDateTime));
  }
}
