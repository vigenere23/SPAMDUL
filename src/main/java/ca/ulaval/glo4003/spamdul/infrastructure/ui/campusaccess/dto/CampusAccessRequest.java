package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;

public class CampusAccessRequest {

  public UserRequest userInfos;
  public CarRequest carInfos;
  public String dayToAccessCampus;
  public String period;
}
