package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class ParkingZoneValidator extends PassValidator {
  private final UserFinderService userReader;

  public ParkingZoneValidator(UserFinderService userFinderService) {
    this.userReader = userFinderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    PassCode passCode = PassCode.valueOf(passToValidateDto.passCode);
    User user = userReader.findBy(passCode);

    if (!user.canParkInZone(passToValidateDto.parkingZone)) {
      throw new WrongZoneInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
