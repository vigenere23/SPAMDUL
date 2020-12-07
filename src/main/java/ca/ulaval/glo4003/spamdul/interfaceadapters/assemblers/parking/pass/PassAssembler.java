package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.PassDto;
import java.util.ArrayList;

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
    final String ERROR_MESSAGE = "make a choice between (single_day_per_week_per_semester, monthly, one_semester," +
        "two_semesters or three_semesters) ";
    TimePeriodDto timePeriodDto;

    try {
      timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);
    } catch (IllegalArgumentException e) {
      throw new InvalidPeriodArgumentException(ERROR_MESSAGE);
    }

    return timePeriodDto;
  }

  private ParkingZone getParkingZone(String parkingZone) {
    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneException("The parking zone is invalid");
    }
  }

  private UserId getUserId(String userId) {
    return UserId.valueOf(userId.toUpperCase());
  }
}
