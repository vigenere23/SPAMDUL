package ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;

public class CampusAccessDtoAssembler {

  public CampusAccessDto toDto(CampusAccess campusAccess) {
    CampusAccessDto campusAccessDto = new CampusAccessDto();
    campusAccessDto.code = campusAccess.getCampusAccessCode();

    return campusAccessDto;
  }

}
