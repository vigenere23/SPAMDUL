package ca.ulaval.glo4003.spamdul.usecases.infraction.dto;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public class InfractionDto {

  public InfractionId id;
  public Amount amount;
  public InfractionCode code;
  public String infractionDescription;
}
