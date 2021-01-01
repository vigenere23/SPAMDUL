package ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user;

import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.car.CarRequest;

public class UserCreationRequest {

  public String name;
  public String birthDate;
  public String gender;
  public CarRequest car;
}
