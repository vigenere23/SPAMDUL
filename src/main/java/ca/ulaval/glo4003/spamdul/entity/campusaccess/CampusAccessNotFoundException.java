package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassSaleArgumentException;

public class CampusAccessNotFoundException extends InvalidPassSaleArgumentException {

  public CampusAccessNotFoundException(String message) {
    super(message);
  }
}
