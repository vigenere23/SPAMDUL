package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import java.util.Set;

public class PermitDto {

  public PermitNumber number;
  public String type;
  public Set<AccessRightDto> accessRights;
  public CarDto car;
}
