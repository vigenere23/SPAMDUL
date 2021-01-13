package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import java.time.LocalDate;

public class InvalidAccessDateException extends InvalidAccessException {

  public InvalidAccessDateException(LocalDate accessDate) {
    super(String.format("Access is invalid for date %s", accessDate));
  }
}
