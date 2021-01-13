package ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions;

import java.time.LocalDateTime;

public class InvalidDateException extends TimePeriodException {

  public InvalidDateException(LocalDateTime localDateTime) {
    super(String.format("%s is out of period days boundaries", localDateTime));
  }
}
