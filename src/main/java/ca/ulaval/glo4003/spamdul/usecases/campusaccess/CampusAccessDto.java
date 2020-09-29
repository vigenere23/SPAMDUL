package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import java.time.DayOfWeek;

public class CampusAccessDto {

  public UserDto userDto;
  public CarDto carDto;
  public DayOfWeek dayToAccessCampus;
  public Period period;
}
