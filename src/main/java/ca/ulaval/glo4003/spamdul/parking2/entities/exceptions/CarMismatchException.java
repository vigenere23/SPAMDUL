package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;

public class CarMismatchException extends InvalidAccessException {

  public CarMismatchException(PermitNumber permitNumber, LicensePlate licensePlate) {
    super(String.format("Permit number %s is not associated to license plate %s", permitNumber, licensePlate));
  }
}
