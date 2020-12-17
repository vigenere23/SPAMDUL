package ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto.CampusAccessDto;

public class CampusAccessDtoAssembler {

  public CampusAccessDto toDto(CampusAccess campusAccess) {
    CampusAccessDto campusAccessDto = new CampusAccessDto();
    campusAccessDto.code = campusAccess.getCampusAccessCode();

    return campusAccessDto;
  }

}
