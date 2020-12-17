package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.parkingzonefee;

public class CantFindParkingZoneFeeException extends RuntimeException {

  public CantFindParkingZoneFeeException() {
    super("Can't find a parking zone fee associated with the given parking zone and period");
  }
}
