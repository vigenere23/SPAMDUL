package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.NoPassInfractionException;

public class EmptyCarParkingPassCodeValidator extends CarParkingPassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    if (passToValidateDto.passCode.equals("")) {
      throw new NoPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }

}
