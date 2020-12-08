package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;

public class ParkingZoneValidatorCarParking extends CarParkingPassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    ParkingPass parkingPass = getCorrespondingPass(CarParkingPassCode.valueOf(passToValidateDto.passCode));

    if (!parkingPass.isAValidParkingZone(passToValidateDto.parkingZone)) {
      throw new InfractionException("ZONE_01");
    }

    nextValidation(passToValidateDto);
  }
}
