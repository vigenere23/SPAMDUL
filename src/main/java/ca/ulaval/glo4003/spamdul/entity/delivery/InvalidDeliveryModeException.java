package ca.ulaval.glo4003.spamdul.entity.delivery;

public class InvalidDeliveryModeException extends RuntimeException{

  public InvalidDeliveryModeException() {
    super("invalid delivery mode");
  }
}
