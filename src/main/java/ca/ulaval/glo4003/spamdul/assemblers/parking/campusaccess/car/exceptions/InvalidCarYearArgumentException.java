package ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.exceptions;

public class InvalidCarYearArgumentException extends InvalidCarArgumentException {

  public String getError() {
    return "INVALID_CAR_YEAR";
  }

  public String getDescription() {
    return "Invalid car year";
  }
}
