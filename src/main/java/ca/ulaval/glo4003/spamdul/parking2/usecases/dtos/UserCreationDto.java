package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.user.Sex;
import java.time.LocalDate;

public class UserCreationDto {

  public String name;
  public Sex sex;
  public LocalDate birthDate;
}
