package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import java.util.Set;

public class ParkingUserDto {

  public ParkingUserId id;
  public String name;
  public Set<PermitDto> permits;
}
