package ca.ulaval.glo4003.spamdul.infrastructure.reader;

public class InvalidJsonFile extends RuntimeException {

  public InvalidJsonFile(String message) {
    super(message);
  }
}
