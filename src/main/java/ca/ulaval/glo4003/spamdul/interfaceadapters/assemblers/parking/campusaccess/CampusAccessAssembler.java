package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess;

import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;
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
    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
      throw new InvalidTimePeriodArgumentException();
    }

    campusAccessDto.timePeriodDto = timePeriodDto;
  }

  public CampusAccessResponse toResponse(CampusAccessDto campusAccess) {
    CampusAccessResponse campusAccessResponse = new CampusAccessResponse();
    campusAccessResponse.campusAccessCode = campusAccess.code.toString();

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
      throw new InvalidCampusAccessArgumentException();
    }
  }

  private void setCampusAccessCode(AccessingCampusRequest accessingCampusRequest,
                                   AccessingCampusDto accessingCampusDto) {
    accessingCampusDto.campusAccessCode = CampusAccessCode.valueOf(accessingCampusRequest.campusAccessCode);
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
