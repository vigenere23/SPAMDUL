package ca.ulaval.glo4003.spamdul.infrastructure.reader;

public class InvalidCsvFile extends RuntimeException {

  public InvalidCsvFile(String message) {
    super(message);
  }

}
