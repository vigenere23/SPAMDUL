package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

public class ParkingUserNotFound extends RuntimeException {

  public ParkingUserNotFound() {
    super("This parking user was not found.");
  }
}
