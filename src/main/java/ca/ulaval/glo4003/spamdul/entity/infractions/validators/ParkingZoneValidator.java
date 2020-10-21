package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public class ParkingZoneValidator extends PassValidator {

  public ParkingZoneValidator(PassRepository passRepository) {
    super(passRepository);
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    Pass pass = getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));

    if (!pass.isAValidParkingZone(passToValidateDto.parkingZone)) {
      throw new InfractionException("ZONE_01");
    }

    super.validate(passToValidateDto);
  }
}
