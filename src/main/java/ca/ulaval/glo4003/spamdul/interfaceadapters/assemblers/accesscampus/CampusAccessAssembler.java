package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import java.time.DayOfWeek;

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
      throw new InvalidPeriodArgumentException("The period must be a single, semester_1, semester_2 or semester_3");
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
}
