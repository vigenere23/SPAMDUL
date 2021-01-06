package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitDeliveryDto;

public class PermitCreationDto {

  public PermitType type;
  public PermitDeliveryDto delivery;
  public CarCreationDto car;
}
