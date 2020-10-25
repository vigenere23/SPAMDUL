package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass;

import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.MONTHLY;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.ONE_SEMESTER;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.THREE_SEMESTERS;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.TWO_SEMESTERS;
import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidCampusAccessCodeFormat;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidCampusAccessCodeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import java.util.ArrayList;

public class PassAssembler {

  private final static ArrayList<PeriodType> ACCEPTED_PERIOD_TYPES = newArrayList(
      SINGLE_DAY_PER_WEEK_PER_SEMESTER,
      MONTHLY,
      ONE_SEMESTER,
      TWO_SEMESTERS,
      THREE_SEMESTERS);
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
    passDto.campusAccessCode = getCampusAccessCode(passCreationRequest.campusAccessCode);

    return passDto;
  }

  private TimePeriodDto getTimePeriodDto(TimePeriodRequest timePeriodRequest) {
    final String ERROR_MESSAGE = "make a choice between (single_day_per_week_per_semester, monthly, one_semester," +
        "two_semester or three_semester) ";
    TimePeriodDto timePeriodDto;

    try {
      timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);
    } catch (IllegalArgumentException e) {
      throw new InvalidPeriodArgumentException(ERROR_MESSAGE);

    }

    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
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

  private CampusAccessCode getCampusAccessCode(String userId) {
    try {
      return CampusAccessCode.valueOf(userId.toUpperCase());

    } catch (InvalidCampusAccessCodeFormat e) {
      throw new InvalidCampusAccessCodeException("The campus access code is not in the right format");
    }
  }
}
