package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidSaleArgumentException;

public class InvalidDeliveryModeException extends InvalidDeliveryArgumentException {

  public InvalidDeliveryModeException(String message) {
    super(message);
  }
}
