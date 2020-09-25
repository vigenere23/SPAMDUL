package ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.user.UserRequest;

public class CampusAccessRequest {

  public UserRequest userRequest;
  public CarRequest carRequest;
  public String dayToAccessCampus;
  public String period;
}
