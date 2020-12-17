package ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionDto {

  public InfractionId id;
  public Amount amount;
  public InfractionCode code;
  public String infractionDescription;
}
