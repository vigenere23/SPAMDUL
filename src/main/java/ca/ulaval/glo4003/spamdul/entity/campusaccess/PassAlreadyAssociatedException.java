package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassSaleArgumentException;

public class PassAlreadyAssociatedException extends InvalidPassSaleArgumentException {

  public PassAlreadyAssociatedException(String message) {
    super(message);
  }
}
