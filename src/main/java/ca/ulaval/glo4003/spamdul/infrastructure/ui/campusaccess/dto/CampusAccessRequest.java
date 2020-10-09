package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;

public class CampusAccessRequest {

  public UserRequest user;
  public CarRequest car;
  public TimePeriodRequest period;
}
