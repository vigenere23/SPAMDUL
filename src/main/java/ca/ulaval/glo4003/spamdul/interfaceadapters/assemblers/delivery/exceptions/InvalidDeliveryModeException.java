package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidDeliveryModeException extends InvalidDeliveryArgumentException {

  public InvalidDeliveryModeException() {
    super("The delivery is either made by post or by mail");
  }
}
