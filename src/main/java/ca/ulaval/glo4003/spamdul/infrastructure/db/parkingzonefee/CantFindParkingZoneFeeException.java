package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingzonefee;

public class CantFindParkingZoneFeeException extends RuntimeException {

  public CantFindParkingZoneFeeException(String message) {
    super(message);
  }
}
