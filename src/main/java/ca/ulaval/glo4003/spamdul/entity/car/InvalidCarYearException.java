package ca.ulaval.glo4003.spamdul.entity.car;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions.InvalidCarArgumentException;

public class InvalidCarYearException extends InvalidCarArgumentException {

  public InvalidCarYearException(String message) {
    super(message);
  }
}
