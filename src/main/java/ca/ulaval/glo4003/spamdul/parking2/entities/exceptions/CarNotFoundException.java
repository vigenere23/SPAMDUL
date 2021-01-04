package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;

public class CarNotFoundException extends InvalidAccessException {

  public CarNotFoundException(LicensePlate licensePlate) {
    super(String.format("Car with license plate %s was not found", licensePlate.toString()));
  }

  public CarNotFoundException(PermitNumber permitNumber) {
    super(String.format("Car with permit number %s was not found", permitNumber.toString()));
  }
}
