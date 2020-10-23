package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public class InvalidPostalAddressException extends InvalidDeliveryArgumentException {

  public InvalidPostalAddressException(String message) {
    super(message);
  }
}
