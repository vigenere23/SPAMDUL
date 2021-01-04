package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import java.time.LocalTime;

public class InvalidAccessTimeException extends InvalidAccessException {

  public InvalidAccessTimeException(LocalTime accessTime) {
    super(String.format("Access is invalid for date %s", accessTime));
  }
}
