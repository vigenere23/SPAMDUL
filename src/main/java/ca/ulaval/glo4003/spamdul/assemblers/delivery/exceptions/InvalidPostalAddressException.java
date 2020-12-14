package ca.ulaval.glo4003.spamdul.assemblers.delivery.exceptions;

public class InvalidPostalAddressException extends InvalidDeliveryArgumentException {

  public String getError() {
    return "INVALID_POSTAL_ADDRESS";
  }

  public String getDescription() {
    return "Only line 2 field is optional in postal address";
  }
}
