package ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.exceptions;

public class InvalidCarTypeArgumentException extends InvalidCarArgumentException {

  public String getError() {
    return "INVALID_CAR_TYPE";
  }

  public String getDescription() {
    return "Invalid car type";
  }
}
