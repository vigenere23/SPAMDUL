package ca.ulaval.glo4003.spamdul.shared.infrastructure.reader;

public class InvalidCsvFile extends RuntimeException {

  public InvalidCsvFile(String filePath) {
    super(String.format("%s is not a valid path", filePath));
  }

}
