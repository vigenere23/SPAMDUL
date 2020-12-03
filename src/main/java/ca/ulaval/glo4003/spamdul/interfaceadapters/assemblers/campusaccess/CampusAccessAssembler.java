package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessCodeCampusArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
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

  private final UserAssembler userAssembler;
  private final CarAssembler carAssembler;
  private final TimePeriodAssembler timePeriodAssembler;

  public CampusAccessAssembler(UserAssembler userAssembler,
                               CarAssembler carAssembler,
                               TimePeriodAssembler timePeriodAssembler) {
    this.userAssembler = userAssembler;
    this.carAssembler = carAssembler;
    this.timePeriodAssembler = timePeriodAssembler;
  }

  public CampusAccessDto fromRequest(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = new CampusAccessDto();
    campusAccessDto.userDto = userAssembler.fromRequest(campusAccessRequest.user);
    campusAccessDto.carDto = carAssembler.fromRequest(campusAccessRequest.car);

    setTimePeriodDto(campusAccessRequest.period, campusAccessDto);

    return campusAccessDto;
  }

  private void setTimePeriodDto(TimePeriodRequest timePeriodRequest, CampusAccessDto campusAccessDto) {
    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
      throw new InvalidTimePeriodArgumentException(ACCEPTED_PERIOD_TYPES.toString());
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
      throw new InvalidAccessCodeCampusArgumentException();
    }
  }

  private void setCampusAccessCode(AccessingCampusRequest accessingCampusRequest,
                                   AccessingCampusDto accessingCampusDto) {
    try {
      accessingCampusDto.campusAccessCode = CampusAccessCode.valueOf(accessingCampusRequest.campusAccessCode);
    } catch (ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidCampusAccessCodeFormatException e) {
      throw new InvalidCampusAccessCodeFormatException();
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
