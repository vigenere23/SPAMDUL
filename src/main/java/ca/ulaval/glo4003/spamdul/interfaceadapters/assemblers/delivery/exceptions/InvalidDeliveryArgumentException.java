package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

public abstract class InvalidDeliveryArgumentException extends RuntimeException {

  protected InvalidDeliveryArgumentException(String message) {
    super(message);
  }
}
