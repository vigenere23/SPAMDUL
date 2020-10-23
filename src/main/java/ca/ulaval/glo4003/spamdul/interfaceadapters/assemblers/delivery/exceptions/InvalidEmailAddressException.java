package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidEmailAddressException extends InvalidDeliveryArgumentException {

  public InvalidEmailAddressException(String message) {
    super(message);
  }
}
