package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidPostalAddressException extends InvalidDeliveryArgumentException {

  public InvalidPostalAddressException() {
    super("Only line 2 field is optional in postal address");
  }
}
