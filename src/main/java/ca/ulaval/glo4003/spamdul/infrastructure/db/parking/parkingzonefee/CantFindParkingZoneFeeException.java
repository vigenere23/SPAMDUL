package ca.ulaval.glo4003.spamdul.infrastructure.db.parking.parkingzonefee;

public class CantFindParkingZoneFeeException extends RuntimeException {

  public CantFindParkingZoneFeeException(String message) {
    super(message);
  }
}
