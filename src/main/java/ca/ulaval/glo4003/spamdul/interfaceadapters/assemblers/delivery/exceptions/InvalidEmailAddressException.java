package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidEmailAddressException extends InvalidDeliveryArgumentException {

  public InvalidEmailAddressException() {
    super("Invalid email address");
  }
}
