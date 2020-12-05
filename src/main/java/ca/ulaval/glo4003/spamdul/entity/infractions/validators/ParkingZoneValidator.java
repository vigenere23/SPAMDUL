package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserReaderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class ParkingZoneValidator extends PassValidator {
  private final UserReaderService userReader;

  public ParkingZoneValidator(UserReaderService userReaderService) {
    this.userReader = userReaderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    PassCode passCode = PassCode.valueOf(passToValidateDto.passCode);
    User user = userReader.readUserBy(passCode);

    if (!user.canParkInZone(passToValidateDto.parkingZone)) {
      throw new WrongZoneInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
