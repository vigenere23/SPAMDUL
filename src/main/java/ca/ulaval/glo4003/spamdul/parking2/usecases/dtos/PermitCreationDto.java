package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;

public class PermitCreationDto {

  public PermitType type;
  public PermitDeliveryDto delivery;
  public CarCreationDto car;
}
