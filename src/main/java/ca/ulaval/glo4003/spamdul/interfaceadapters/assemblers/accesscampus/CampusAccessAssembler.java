package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidCampusAccessCodeFormat;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidAccessingCampusDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidCampusAccessCodeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CampusAccessAssembler {

  private final UserAssembler userAssembler;
  private final CarAssembler carAssembler;

  public CampusAccessAssembler(UserAssembler userAssembler, CarAssembler carAssembler) {
    this.userAssembler = userAssembler;
    this.carAssembler = carAssembler;
  }

  public CampusAccessDto fromRequest(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = new CampusAccessDto();
    campusAccessDto.userDto = userAssembler.fromRequest(campusAccessRequest.userInfos);
    campusAccessDto.carDto = carAssembler.fromRequest(campusAccessRequest.carInfos);

    setDayToAccessCampus(campusAccessRequest, campusAccessDto);
    setPeriod(campusAccessRequest, campusAccessDto);

    return campusAccessDto;
  }

  private void setPeriod(CampusAccessRequest campusAccessRequest, CampusAccessDto campusAccessDto) {
    try {
      campusAccessDto.period = Period.valueOf(campusAccessRequest.period.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidPeriodArgumentException(
          "The period must be a single, single_day_per_week_per_semester, semester_1, semester_2 or semester_3");
    }
  }

  private void setDayToAccessCampus(CampusAccessRequest campusAccessRequest, CampusAccessDto campusAccessDto) {
    try {
      campusAccessDto.dayToAccessCampus = DayOfWeek.valueOf(campusAccessRequest.dayToAccessCampus.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidDayOfCampusAccessArgumentException("The day to access campus must be a valid day of the week");
    }
  }

  public CampusAccessResponse toResponse(CampusAccess campusAccess) {
    CampusAccessResponse campusAccessResponse = new CampusAccessResponse();
    campusAccessResponse.campusAccessCode = campusAccess.getCampusAccessCode().toString();

    return campusAccessResponse;
  }

  public AccessingCampusDto fromRequest(AccessingCampusRequest accessingCampusRequest) {
    AccessingCampusDto accessingCampusDto = new AccessingCampusDto();

    setCampusAccessCode(accessingCampusRequest, accessingCampusDto);
    setAccessingCampusDate(accessingCampusRequest, accessingCampusDto);

    return accessingCampusDto;
  }

  private void setAccessingCampusDate(AccessingCampusRequest accessingCampusRequest,
                                      AccessingCampusDto accessingCampusDto) {
    try {
      accessingCampusDto.accessingCampusDate = LocalDate.parse(accessingCampusRequest.accessingCampusDate,
                                                               DateTimeFormatter.ACCESSING_CAMPUS_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidAccessingCampusDateArgumentException("The accessing campus date must be yyyy-MM-dd");
    }
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
