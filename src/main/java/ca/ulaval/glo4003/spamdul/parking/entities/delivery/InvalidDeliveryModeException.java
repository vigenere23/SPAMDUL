package ca.ulaval.glo4003.spamdul.parking.entities.delivery;

public class InvalidDeliveryModeException extends RuntimeException {

  public InvalidDeliveryModeException() {
    super("Invalid delivery mode");
  }
}
