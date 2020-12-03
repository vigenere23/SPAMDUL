package ca.ulaval.glo4003.spamdul.infrastructure.reader;

public class InvalidCsvFile extends RuntimeException {

  public InvalidCsvFile(String filePath) {
    super(String.format("%s is not a valid path", filePath));
  }

}
