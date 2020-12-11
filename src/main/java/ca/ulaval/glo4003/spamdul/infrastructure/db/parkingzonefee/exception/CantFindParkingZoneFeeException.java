package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingzonefee.exception;

public class CantFindParkingZoneFeeException extends RuntimeException {

  public CantFindParkingZoneFeeException() {
    super("Can't find a parking zone fee associated with the given parking zone and period");
  }
}
