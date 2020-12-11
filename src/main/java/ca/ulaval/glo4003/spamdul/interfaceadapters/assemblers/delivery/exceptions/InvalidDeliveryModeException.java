package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidDeliveryModeException extends InvalidDeliveryArgumentException {

  public String getError() {
    return "INVALID_DELIVERY_MODE";
  }

  public String getDescription() {
    return "The delivery is either made by post or by mail";
  }
}
