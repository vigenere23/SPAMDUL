package ca.ulaval.glo4003.spamdul.usecases.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class UserDto {

  public String name;
  public Gender gender;
  public LocalDate birthDate;
  public DayOfWeek dayToAccessCampus;
}
