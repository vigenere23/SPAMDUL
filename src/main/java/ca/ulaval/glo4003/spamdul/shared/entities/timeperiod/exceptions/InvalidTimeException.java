package ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions;

import java.time.LocalDateTime;

public class InvalidTimeException extends TimePeriodException {

  public InvalidTimeException(LocalDateTime localDateTime) {
    super(String.format("%s is out of period time boundaries", localDateTime));
  }
}
