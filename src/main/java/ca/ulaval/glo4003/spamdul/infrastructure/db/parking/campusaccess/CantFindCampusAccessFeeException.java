package ca.ulaval.glo4003.spamdul.infrastructure.db.parking.campusaccess;

public class CantFindCampusAccessFeeException extends RuntimeException {

  public CantFindCampusAccessFeeException(String message) {
    super(message);
  }
}
