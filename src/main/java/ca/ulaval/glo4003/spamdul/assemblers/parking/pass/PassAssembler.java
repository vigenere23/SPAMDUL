package ca.ulaval.glo4003.spamdul.assemblers.parking.pass;

import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.PassDto;

public class PassAssembler {

  private final DeliveryAssembler deliveryAssembler;
  private final TimePeriodAssembler timePeriodAssembler;

  public PassAssembler(DeliveryAssembler deliveryAssembler, TimePeriodAssembler timePeriodAssembler) {
    this.deliveryAssembler = deliveryAssembler;
    this.timePeriodAssembler = timePeriodAssembler;
  }

  public PassDto fromRequest(PassCreationRequest passCreationRequest) {
    PassDto passDto = new PassDto();

    passDto.deliveryDto = deliveryAssembler.fromRequest(passCreationRequest.delivery);
    passDto.timePeriodDto = getTimePeriodDto(passCreationRequest.period);
    passDto.parkingZone = getParkingZone(passCreationRequest.parkingZone);
    passDto.userId = getUserId(passCreationRequest.userId);

    return passDto;
  }

  private TimePeriodDto getTimePeriodDto(TimePeriodRequest timePeriodRequest) {
    TimePeriodDto timePeriodDto;

    try {
      timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);
    } catch (IllegalArgumentException e) {
      throw new InvalidTimePeriodArgumentException();
    }

    return timePeriodDto;
  }

  private ParkingZone getParkingZone(String parkingZone) {
    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneException();
    }
  }

  private UserId getUserId(String userId) {
    return UserId.valueOf(userId.toUpperCase());
  }
}
