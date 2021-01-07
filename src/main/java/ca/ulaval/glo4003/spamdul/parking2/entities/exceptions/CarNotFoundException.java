package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class CarNotFoundException extends InvalidAccessException {

  public CarNotFoundException(LicensePlate licensePlate) {
    super(String.format("No car found with license plate %s", licensePlate.toString()));
  }
}
