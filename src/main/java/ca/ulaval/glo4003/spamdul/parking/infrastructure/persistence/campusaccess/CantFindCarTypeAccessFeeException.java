package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.campusaccess;

public class CantFindCarTypeAccessFeeException extends RuntimeException {

  public CantFindCarTypeAccessFeeException() {
    super("Can't find cat type access fee");
  }
}
