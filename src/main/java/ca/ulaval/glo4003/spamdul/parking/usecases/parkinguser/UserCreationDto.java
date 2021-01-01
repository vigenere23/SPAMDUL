package ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.Gender;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.car.CarDto;
import java.time.LocalDate;

public class UserCreationDto {

  public String name;
  public Gender gender;
  public LocalDate birthDate;
  public CarDto carDto;
}
