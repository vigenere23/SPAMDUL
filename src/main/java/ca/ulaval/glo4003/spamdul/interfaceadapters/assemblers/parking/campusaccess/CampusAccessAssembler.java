package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess;

import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.InvalidCampusAccessCodeFormatException;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidAccessingCampusArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidCampusAccessCodeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessDto;
import java.util.ArrayList;

public class CampusAccessAssembler {

  private static final ArrayList<PeriodType> ACCEPTED_PERIOD_TYPES = newArrayList(
      PeriodType.HOURLY,
      PeriodType.SINGLE_DAY,
      PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER,
      PeriodType.ONE_SEMESTER,
      PeriodType.TWO_SEMESTERS,
      PeriodType.THREE_SEMESTERS
  );

  private final TimePeriodAssembler timePeriodAssembler;

  public CampusAccessAssembler(TimePeriodAssembler timePeriodAssembler) {
    this.timePeriodAssembler = timePeriodAssembler;
  }

  public CampusAccessDto fromRequest(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = new CampusAccessDto();

    setTimePeriodDto(campusAccessRequest.period, campusAccessDto);
    setUserId(campusAccessRequest.userId, campusAccessDto);

    return campusAccessDto;
  }

  private void setUserId(String userIdString, CampusAccessDto campusAccessDto) {
    campusAccessDto.userId = UserId.valueOf(userIdString);
  }

  private void setTimePeriodDto(TimePeriodRequest timePeriodRequest, CampusAccessDto campusAccessDto) {
    final String ERROR_MESSAGE = "make a choice between: " + ACCEPTED_PERIOD_TYPES.toString();

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
      throw new InvalidPeriodArgumentException(ERROR_MESSAGE);
    }

    campusAccessDto.timePeriodDto = timePeriodDto;
  }

  public CampusAccessResponse toResponse(CampusAccess campusAccess) {
    CampusAccessResponse campusAccessResponse = new CampusAccessResponse();
    campusAccessResponse.campusAccessCode = campusAccess.getCampusAccessCode().toString();

    return campusAccessResponse;
  }

  public AccessingCampusDto fromRequest(AccessingCampusRequest accessingCampusRequest) {
    AccessingCampusDto accessingCampusDto = new AccessingCampusDto();

    if (accessingCampusRequest.campusAccessCode != null) {
      setCampusAccessCode(accessingCampusRequest, accessingCampusDto);
    } else {
      setCarLicensePlate(accessingCampusRequest, accessingCampusDto);
    }

    return accessingCampusDto;
  }

  private void setCarLicensePlate(AccessingCampusRequest accessingCampusRequest,
                                  AccessingCampusDto accessingCampusDto) {
    if (accessingCampusRequest.licensePlate != null) {
      accessingCampusDto.licensePlate = new LicensePlate(accessingCampusRequest.licensePlate);
    } else {
      throw new InvalidAccessingCampusArgumentException("A campus access code or a license plate must be provided");
    }
  }

  private void setCampusAccessCode(AccessingCampusRequest accessingCampusRequest,
                                   AccessingCampusDto accessingCampusDto) {
    try {
      accessingCampusDto.campusAccessCode = CampusAccessCode.valueOf(accessingCampusRequest.campusAccessCode);
    } catch (InvalidCampusAccessCodeFormatException e) {
      throw new InvalidCampusAccessCodeArgumentException("The access campus code is not in the right format");
    }
  }

  public AccessingCampusResponse toResponse(boolean isAccessGranted) {
    AccessingCampusResponse response = new AccessingCampusResponse();

    if (isAccessGranted) {
      response.access = "GRANTED";
    } else {
      response.access = "NOT GRANTED";
    }

    return response;
  }
}
