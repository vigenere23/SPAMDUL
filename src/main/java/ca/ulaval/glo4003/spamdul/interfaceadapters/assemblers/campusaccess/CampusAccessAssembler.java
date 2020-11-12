package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidCampusAccessCodeFormat;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessCodeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import java.util.ArrayList;

public class CampusAccessAssembler {

  private static final ArrayList<PeriodType> ACCEPTED_PERIOD_TYPES = newArrayList(
      PeriodType.SINGLE_DAY,
      PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER,
      PeriodType.ONE_SEMESTER,
      PeriodType.TWO_SEMESTERS,
      PeriodType.THREE_SEMESTERS);

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
    final String ERROR_MESSAGE = "make a choice between (single_day, single_day_per_week_per_semester, one_semester," +
        "two_semester or three_semester) ";

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

    setCampusAccessCode(accessingCampusRequest, accessingCampusDto);

    return accessingCampusDto;
  }

  private void setCampusAccessCode(AccessingCampusRequest accessingCampusRequest,
                                   AccessingCampusDto accessingCampusDto) {
    try {
      accessingCampusDto.campusAccessCode = CampusAccessCode.valueOf(accessingCampusRequest.campusAccessCode);
    } catch (InvalidCampusAccessCodeFormat e) {
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
