package ca.ulaval.glo4003.spamdul.assemblers.delivery.exceptions;

public class InvalidEmailAddressException extends InvalidDeliveryArgumentException {

  public InvalidEmailAddressException() {
    super();
  }

  public String getError() {
    return "INVALID_EMAIL_ADDRESS";
  }

  public String getDescription() {
    return "Invalid email address";
  }
}
