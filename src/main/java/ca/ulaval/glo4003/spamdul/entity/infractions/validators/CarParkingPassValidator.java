package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public abstract class CarParkingPassValidator {

  private CarParkingPassValidator nextCarParkingPassValidator;

  public void setNextValidator(CarParkingPassValidator carParkingPassValidator) {
    nextCarParkingPassValidator = carParkingPassValidator;
  }

  public abstract void validate(PassToValidateDto passToValidateDto);

  protected void nextValidation(PassToValidateDto passToValidateDto) {
    if (nextCarParkingPassValidator != null) {
      nextCarParkingPassValidator.validate(passToValidateDto);
    }
  }

}
