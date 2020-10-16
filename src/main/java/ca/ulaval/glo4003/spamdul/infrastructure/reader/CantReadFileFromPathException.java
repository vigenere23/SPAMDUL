package ca.ulaval.glo4003.spamdul.infrastructure.reader;

public class CantReadFileFromPathException extends RuntimeException{

  public CantReadFileFromPathException(String message) {
    super(message);
  }
}
