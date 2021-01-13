package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import java.util.Set;

public class ParkingUserDto {

  public AccountId accountId;
  public String name;
  public Set<PermitDto> permits;
}
