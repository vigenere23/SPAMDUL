package ca.ulaval.glo4003.spamdul.parking2.api.dtos;

import java.util.Set;

public class ParkingUserResponse {

  public String id;
  public String name;
  public Set<PermitResponse> permits;
}
