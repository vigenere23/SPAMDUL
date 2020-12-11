package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.exception;

public class CantFindCarTypeAccessFeeException extends RuntimeException {

  public CantFindCarTypeAccessFeeException() {
    super("Can't find cat type access fee");
  }
}
