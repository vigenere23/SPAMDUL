package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidUserIdException;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;

public class PassAssembler {

  public PassDto fromDTO(PassRequest passRequest) {
    PassDto passDto = new PassDto();

    passDto.parkingZone = getParkingZone(passRequest.parkingZone);
    passDto.userId = getUserId(passRequest.userId);
    passDto.passType = getPassType(passRequest.passType);

    return passDto;
  }

  private ParkingZone getParkingZone(String parkingZone) {
    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneException("The parking zone is invalid");
    }
  }

  private PassType getPassType(String passType) {
    try {
      return PassType.valueOf(passType.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidPassTypeException("The pass type is invalid");
    }
  }

  private UserId getUserId(String userId) {
    try {
      return UserId.valueOf(userId.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidUserIdException("The user ID must be valid");
    }
  }

}
