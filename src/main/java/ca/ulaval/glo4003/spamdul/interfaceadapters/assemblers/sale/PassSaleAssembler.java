package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidUserIdException;
import ca.ulaval.glo4003.spamdul.usecases.sale.PassSaleDto;

public class PassSaleAssembler {

  private DeliveryAssembler deliveryAssembler;

  public PassSaleAssembler(DeliveryAssembler deliveryAssembler) {
    this.deliveryAssembler = deliveryAssembler;
  }

  public PassSaleDto fromDto(PassSaleRequest passSaleRequest) {
    PassSaleDto passSaleDto = new PassSaleDto();

    passSaleDto.deliveryDto = deliveryAssembler.fromDto(passSaleRequest.deliveryRequest);
    passSaleDto.parkingZone = getParkingZone(passSaleRequest.parkingZone);
    passSaleDto.userId = getUserId(passSaleRequest.userId);
    passSaleDto.passType = getPassType(passSaleRequest.passType);

    return passSaleDto;
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
