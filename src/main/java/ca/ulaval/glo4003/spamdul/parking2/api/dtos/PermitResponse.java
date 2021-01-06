package ca.ulaval.glo4003.spamdul.parking2.api.dtos;

import java.util.Set;

public class PermitResponse {

  public String number;
  public String type;
  public CarResponse car;
  public Set<AccessRightResponse> accessRights;
}
