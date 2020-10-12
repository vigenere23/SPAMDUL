package ca.ulaval.glo4003.spamdul.infrastructure.reader;

public class CantFindFileException extends RuntimeException {

  public CantFindFileException(String message) {
    super(message);
  }

}