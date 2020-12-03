package ca.ulaval.glo4003.spamdul.usecases.campusaccess.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import java.time.LocalDate;

public class UserDto {

  public String name;
  public Gender gender;
  public LocalDate birthDate;
  public CarDto carDto;
}
