package ca.ulaval.glo4003.spamdul.entity.user.car;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.exceptions.InvalidCarArgumentException;
import java.time.LocalDate;

public class InvalidCarYearException extends InvalidCarArgumentException {

  public String getError() {
    return "INVALID_CAR_YEAR";
  }

  public String getDescription() {
    return String.format("Invalid car year. The car year must be under %s", LocalDate.now().getYear());
  }
}
