package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;

public class PermitNotFoundException extends InvalidAccessException {

  public PermitNotFoundException(PermitNumber permitNumber) {
    super(String.format("Permit with number %s was not found.", permitNumber));
  }

  public PermitNotFoundException(LicensePlate licensePlate) {
    super(String.format("Permit associated with license plate %s was not found.", licensePlate));
  }
}
