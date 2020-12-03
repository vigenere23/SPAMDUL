package ca.ulaval.glo4003.spamdul.entity.car;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions.InvalidCarArgumentException;
import java.time.LocalDate;

public class InvalidCarYearException extends InvalidCarArgumentException {

  public InvalidCarYearException() {
    super(String.format("Invalid car year. The car year must be under %s", LocalDate.now().getYear()));
  }
}
