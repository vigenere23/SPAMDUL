package ca.ulaval.glo4003.spamdul.assemblers.parking.pass;

import ca.ulaval.glo4003.spamdul.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.parking.api.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass.PassCreationDto;
import ca.ulaval.glo4003.spamdul.time.api.timeperiod.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;

public class PassAssembler {

  private final DeliveryAssembler deliveryAssembler;
  private final TimePeriodAssembler timePeriodAssembler;

  public PassAssembler(DeliveryAssembler deliveryAssembler, TimePeriodAssembler timePeriodAssembler) {
    this.deliveryAssembler = deliveryAssembler;
    this.timePeriodAssembler = timePeriodAssembler;
  }

  public PassCreationDto fromRequest(PassCreationRequest passCreationRequest) {
    PassCreationDto passCreationDto = new PassCreationDto();

    passCreationDto.deliveryDto = deliveryAssembler.fromRequest(passCreationRequest.delivery);
    passCreationDto.timePeriodDto = getTimePeriodDto(passCreationRequest.period);
    passCreationDto.parkingZone = getParkingZone(passCreationRequest.parkingZone);
    passCreationDto.userId = getUserId(passCreationRequest.userId);

    return passCreationDto;
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
